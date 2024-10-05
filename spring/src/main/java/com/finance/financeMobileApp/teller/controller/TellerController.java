package com.finance.financeMobileApp.teller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.financeMobileApp.teller.service.TellerService;

import reactor.core.publisher.Mono;

@RestController
public class TellerController {
    private static final Logger logger = LoggerFactory.getLogger(TellerController.class);
    
    private final TellerService tellerService;

    @Autowired
    public TellerController(TellerService tellerService) {
        this.tellerService = tellerService;
    }

    @GetMapping("/api/accounts")
    public Mono<String> getAccounts() {
        // Call the TellerService to fetch account data
        return tellerService.getAccounts();
    }
}