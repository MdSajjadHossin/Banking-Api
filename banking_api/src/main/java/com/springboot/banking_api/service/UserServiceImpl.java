package com.springboot.banking_api.service;

import com.springboot.banking_api.dto.*;
import com.springboot.banking_api.entity.User;
import com.springboot.banking_api.repository.UserRepository;
import com.springboot.banking_api.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public BankingResponse createAccount(UserRequest userRequest) {

        if(userRepository.existsByEmail(userRequest.getEmail())) {
            return BankingResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
            User newUser = User.builder()
                    .firstName(userRequest.getFirstName())
                    .lastName(userRequest.getLastName())
                    .gender(userRequest.getGender())
                    .address(userRequest.getAddress())
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .email(userRequest.getEmail())
                    .phoneNumber(userRequest.getPhoneNumber())
                    .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                    .status("ACTIVE")
                    .build();

        User savedUser = userRepository.save(newUser);
        // send email alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations your has been successfully created.\n" + "Your account details: \n" + "Account Holder Name: " + savedUser.getFirstName() +
                        " "+ savedUser.getLastName()+
                        "\n Account Number: "+ savedUser.getAccountNumber()+
                        "\n Created At: " + savedUser.getCreatedAt()
                )
                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankingResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName()+" "+savedUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankingResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isAccountExists){
            return BankingResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankingResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(foundUser.getFirstName() + " "+foundUser.getLastName())
                        .accountBalance(foundUser.getAccountBalance())
                        .accountNumber(foundUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isAccountExists){
            return AccountUtils.ACCOUNT_FOUND_MESSAGE;
        }

        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return foundUser.getFirstName()+" "+foundUser.getLastName();
    }

    @Override
    public BankingResponse creditAccount(CreditDebitRequest creditDebitRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if(!isAccountExists){
            return BankingResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitRequest.getAmount()));
        userRepository.save(userToCredit);

        return BankingResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName())
                        .accountBalance(userToCredit.getAccountBalance())
                        .accountNumber(userToCredit.getAccountNumber())
                        .build())
                .build();


    }

    @Override
    public BankingResponse debitAccount(CreditDebitRequest creditDebitRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(creditDebitRequest.getAccountNumber());
        if(!isAccountExists){
            return BankingResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToDebit = userRepository.findByAccountNumber(creditDebitRequest.getAccountNumber());
        BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount = creditDebitRequest.getAmount().toBigInteger();

        if(availableBalance.intValue() < debitAmount.intValue()){
            return BankingResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .accountInfo(null)
                    .build();
        }else{
            userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitRequest.getAmount()));
            userRepository.save(userToDebit);
            return BankingResponse.builder()
                    .responseCode(AccountUtils.AMOUNT_DEBITED_SUCCESSFUL_CODE)
                    .responseMessage(AccountUtils.AMOUNT_DEBITED_SUCCESSFUL_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountName(userToDebit.getFirstName() + " " + userToDebit.getFirstName())
                            .accountBalance(userToDebit.getAccountBalance())
                            .accountNumber(userToDebit.getAccountNumber())
                            .build())
                    .build();
        }


    }

    @Override
    public BankingResponse transfer(TransferRequest transferRequest) {
         // get the account to debit (check if it exists)
        boolean isDestinationAccountExists = userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());

        if(!isDestinationAccountExists){
            return BankingResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        // check if the amount i'm debiting is not more than current balance;
        User sourceAccountUser = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        if(transferRequest.getAmount().compareTo(sourceAccountUser.getAccountBalance()) >  0){
            return BankingResponse.builder()
                    .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .accountInfo(null)
                    .build();
        }
        // debit the amount;
        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
        userRepository.save(sourceAccountUser);

        String sourceUserName = sourceAccountUser.getFirstName() + " " + sourceAccountUser.getLastName();
        EmailDetails detailsAlert = EmailDetails.builder()
                .subject("DEBIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("Total amount of " + transferRequest.getAmount() + " " + "has been debited from your account to " + transferRequest.getDestinationAccountNumber() + "\n" + "A/C holder name: " + sourceUserName + "\n" + "Your current balance is: " + sourceAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(detailsAlert);

        // credit the account;
        User destinationAccountUser = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
        userRepository.save(destinationAccountUser);

        //String recipientUserName = destinationAccountUser.getFirstName() + " " + destinationAccountUser.getLastName();
        EmailDetails creditAlert = EmailDetails.builder()
                .subject("CREDIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("Total amount of " + transferRequest.getAmount() + " " + "has been credited to your account from " + transferRequest.getSourceAccountNumber() + "\n" + " A/C holder name: "+ "\n" + " Your current balance is : " + destinationAccountUser.getAccountBalance())
                .build();
        emailService.sendEmailAlert(creditAlert);

        return BankingResponse.builder()
                .responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null)
                .build();
    }


}
