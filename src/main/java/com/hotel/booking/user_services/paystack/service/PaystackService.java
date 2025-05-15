package com.hotel.booking.user_services.paystack.service;

import com.hotel.booking.user_services.dto.ResponseModel;
import com.hotel.booking.user_services.paystack.dto.PaystackPaymentDto;

public interface PaystackService {
    ResponseModel initializeTransaction(PaystackPaymentDto dto);
    
    ResponseModel verifyTransaction(String reference);

    ResponseModel listTransactions(int perPage, String nextPage);

    ResponseModel fetchTransaction(String reference);

    // ResponseModel fetchTransactions(String loanCaseCode);

}

