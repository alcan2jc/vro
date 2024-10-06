package com.finance.financeMobileApp.teller.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finance.financeMobileApp.teller.service.TellerService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/teller")
public class TellerController {
    private static final Logger logger = LoggerFactory.getLogger(TellerController.class);
    
    private final TellerService tellerService;
    private String accessToken = "";

    @Autowired
    public TellerController(TellerService tellerService) {
        this.tellerService = tellerService;
    }

    @PostMapping("/link-account")
    public String linkAccount(@RequestBody String accountData) {
        return tellerService.linkAccount(accountData);
    }

    @GetMapping("/accounts")
    public Mono<String> getAccounts() {
        // Call the TellerService to fetch account data
        return tellerService.getAccounts(accessToken);
    }

    @GetMapping("/accounts")
    public Mono<String> getBalances() {
        // Call the TellerService to fetch account data
        return tellerService.getAccounts(accessToken);
    }


}