package com.finance.financeMobileApp.teller.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.finance.financeMobileApp.teller.config.WebclientConfig;

import reactor.core.publisher.Mono;

@Service
public class TellerService {
    private static final Logger logger = LoggerFactory.getLogger(TellerService.class);
    private final WebclientConfig webClientConfig;

    public TellerService(WebclientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    // Example method to call Teller API
    public Mono<String> getAccounts() {
        logger.info("getAccounts");
        try {
            return webClientConfig
                .webClient()
                .get()
                .uri("/accounts")
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