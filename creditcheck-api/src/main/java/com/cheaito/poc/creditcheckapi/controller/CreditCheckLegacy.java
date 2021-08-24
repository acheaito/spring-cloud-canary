package com.cheaito.poc.creditcheckapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCheckLegacy {

    @RequestMapping(path="/v1/creditcheck", produces = "text/plain;charset=UTF-8")
    public String checkCredit() {
        return "LEGACY";
    }

}
