package com.springboot.banking_api.controller;

import com.springboot.banking_api.dto.*;
import com.springboot.banking_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public BankingResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public BankingResponse balanceEnquiry(@RequestBody EnquiryRequest acNumber){
        return userService.balanceEnquiry(acNumber);
    }

    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest acNumber){
        return userService.nameEnquiry(acNumber);
    }

    @PostMapping("/credit")
    public BankingResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debit")
    public BankingResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @PostMapping("/transfer")
    public BankingResponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }

}
