package com.springboot.banking_api.service;

import com.springboot.banking_api.dto.*;

public interface UserService {

    BankingResponse createAccount(UserRequest userRequest);
    BankingResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest request);
    BankingResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankingResponse debitAccount(CreditDebitRequest creditDebitRequest);
    BankingResponse transfer(TransferRequest transferRequest);

}
