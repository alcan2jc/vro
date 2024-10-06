package com.finance.financeMobileApp.teller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.finance.financeMobileApp.teller.config.WebclientConfig;

import reactor.core.publisher.Mono;

@Service
public class TellerService {
    private static final Logger logger = LoggerFactory.getLogger(TellerService.class);
    private final WebclientConfig webClientConfig;
    private final RestTemplate restTemplate;

    public TellerService(WebclientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
        restTemplate = this.webClientConfig.restTemplate();
    }

    public String linkAccount(String accountData) {
        String tellerApiUrl = "https://api.teller.io/accounts"; // Replace with the correct endpoint
        // Set your headers and request body accordingly
        return restTemplate.postForObject(tellerApiUrl, accountData, String.class);
    }

    // Example method to call Teller API
    public Mono<String> getAccounts(String accessToken) {
        logger.info("getAccounts");
        try {
            return webClientConfig
                .webClient()
                .get()
                .uri("/accounts")
                .headers(headers -> headers.setBasicAuth(accessToken, ""))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(ex -> {
                    // Log the exception (optional)
                    System.err.println("Error occurred while fetching accounts: " + ex.getMessage());
                    
                    // Return a fallback Mono with an error message
                    return Mono.just("Failed to fetch accounts: " + ex.getMessage());
                });
        } catch(Exception e) {
            logger.info(e.toString());
            return null;
        }
    }
}