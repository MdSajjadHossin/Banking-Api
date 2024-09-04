package com.springboot.banking_api.utils;

import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXIST_CODE = "001";
    public static  final String ACCOUNT_EXIST_MESSAGE = "Account already exist";
    public static final String ACCOUNT_FOUND_CODE = "00";
    public static  final String ACCOUNT_FOUND_MESSAGE = "Account Found...";
    public static final String ACCOUNT_CREATED_CODE = "002";
    public static final String ACCOUNT_CREATION_MESSAGE = "Account created";
    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";
    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "User with this credential does not exist";
    public static final String USER_NOT_FOUND_CODE="004";
    public static final String USER_NOT_FOUND_MESSAGE ="Account not found";
    public static final String INSUFFICIENT_BALANCE_CODE = "005";
    public static final String INSUFFICIENT_BALANCE_MESSAGE = "Insufficient Balance";
    public static final String AMOUNT_DEBITED_SUCCESSFUL_CODE = "006";
    public static final String AMOUNT_DEBITED_SUCCESSFUL_MESSAGE = "Amount debited successful";



    public static String generateAccountNumber(){
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        int randNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);

        StringBuilder accountNumber = new StringBuilder();

        return accountNumber.append(year).append(randomNumber).toString();
    }
}
