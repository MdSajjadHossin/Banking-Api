package com.springboot.banking_api.controller;

import com.springboot.banking_api.dto.*;
import com.springboot.banking_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User endpoints")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Create new account",
            description = "To creating a new account it requires fastName, lastName, email, gender, address, phoneNumber, alternativePhoneNumber"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @PostMapping("/")
    public BankingResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);
    }

    @Operation(
            summary = "Balance Enquiry endpoint",
            description = "For searching a account it requires accountNumber in the request body"
    )
    @ApiResponse(
            responseCode = "002",
            description = "Http status 002 ACCOUNT_FOUND"
    )
    @GetMapping("/balanceEnquiry")
    public BankingResponse balanceEnquiry(@RequestBody EnquiryRequest acNumber){
        return userService.balanceEnquiry(acNumber);
    }

    @Operation(
            summary = "Name Enquiry endpoint",
            description = "For searching a account it requires name in the request body"
    )
    @ApiResponse(
            responseCode = "002",
            description = "Http status 002 ACCOUNT_FOUND"
    )
    @GetMapping("/nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequest acNumber){
        return userService.nameEnquiry(acNumber);
    }

    @Operation(
            summary = "Balance credit endpoint",
            description = "For crediting form a account it requires accountNumber and amount in the request body to perform the operation"
    )
    @ApiResponse(
            responseCode = "007",
            description = "Http status 007 AMOUNT_CREDITED_SUCCESSFUL_MESSAGE"
    )
    @PostMapping("/credit")
    public BankingResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @Operation(
            summary = "Balance debited endpoint",
            description = "For debited form a account it requires accountNumber and amount in the request body to perform the operation"
    )
    @ApiResponse(
            responseCode = "006",
            description = "Http status 006 AMOUNT_DEBITED_SUCCESSFUL_MESSAGE"
    )
    @PostMapping("/debit")
    public BankingResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }

    @Operation(
            summary = "Balance transfer endpoint",
            description = "For crediting form a account it requires sourceAccountNumber, destinationAccountNumber and amount in the request body to perform the operation"
    )
    @ApiResponse(
            responseCode = "008",
            description = "Http status 008 TRANSFER_SUCCESSFUL_MESSAGE"
    )
    @PostMapping("/transfer")
    public BankingResponse transfer(@RequestBody TransferRequest transferRequest){
        return userService.transfer(transferRequest);
    }

}
