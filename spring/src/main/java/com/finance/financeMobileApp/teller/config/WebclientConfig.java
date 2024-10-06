package com.finance.financeMobileApp.teller.config;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContextBuilder;
import jakarta.annotation.PostConstruct;
import reactor.netty.http.client.HttpClient;

@Configuration
@PropertySource("classpath:application.properties")
public class WebclientConfig {

    @Value("${server.ssl.key-store-password}")
    private String keystorePassword;

    @Value("${server.ssl.key-store}")
    private String keystorePath;

    @PostConstruct
    public void init() {
        System.out.println("Key Store: " + keystorePath);
    }

    @Bean
    public WebClient webClient() throws Exception {
        // Load the keystore containing the client certificate
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream inputStream = new FileInputStream(keystorePath)) {
            keyStore.load(inputStream, keystorePassword.toCharArray());
        }
        
        // Get the alias of the first key in the keystore
        String alias = keyStore.aliases().nextElement(); // Get the first alias
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keystorePassword.toCharArray());
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);

        if (privateKey == null || certificate == null) {
            throw new IllegalArgumentException("Key or certificate not found for alias: " + alias);
        }

        // Create KeyManagerFactory and TrustManagerFactory
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keystorePassword.toCharArray());
        
        // Create the Netty SslContext
        io.netty.handler.ssl.SslContext nettySslContext = SslContextBuilder.forClient()
                .keyManager(keyManagerFactory)
                .build();


        // Create WebClient using the configured HTTP client
        return WebClient.builder()
                        .clientConnector(new ReactorClientHttpConnector(HttpClient.create().secure(ssl -> ssl.sslContext(nettySslContext))))
                        .baseUrl("https://api.teller.io") // Base URL for Teller API
                        .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
