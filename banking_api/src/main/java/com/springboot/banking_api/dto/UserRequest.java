package com.springboot.banking_api.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class UserRequest {

    private String firstName;
    private String lastName;
    private String gender;
    private String address;
    private String accountNumber;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
}
