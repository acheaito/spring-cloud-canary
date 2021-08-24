package com.cheaito.poc.creditcheckapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCheckModern {

    @RequestMapping(path="/v2/creditcheck", produces = "text/plain;charset=UTF-8")
    public String checkCredit() {
        return "MODERN";
    }

}
