package com.hotel.booking.hotel_services.paystack.serviceImpl;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.booking.hotel_services.dto.ResponseModel;
import com.hotel.booking.hotel_services.paystack.entity.PaystackTransaction;
import com.hotel.booking.hotel_services.paystack.repository.PaystackTransactionRepository;
import com.hotel.booking.hotel_services.paystack.service.PaystackService;
import com.hotel.booking.hotel_services.paystack.service.PaystackWebhookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaystackWebhookServiceImpl implements PaystackWebhookService {
    private final PaystackTransactionRepository paystackTransactionRepository;
    private final ObjectMapper objectMapper;
    private final PaystackService paystackService;
    @Override
    public boolean processWebhookEvent(String payload) {
        try {
            // Parse the JSON payload
            @SuppressWarnings("unchecked")
            Map<String, Object> eventPayload = objectMapper.readValue(payload, Map.class);

            // Check event type
            String event = (String) eventPayload.get("event");
            if ("charge.success".equals(event)) {
                // Process successful payment
                @SuppressWarnings("unchecked")
                Map<String, Object> data = (Map<String, Object>) eventPayload.get("data");
                String reference = (String) data.get("reference");
                int amount = (Integer) data.get("amount");
                String status = (String) data.get("status");

                // Store transaction information in the database
                PaystackTransaction transaction = paystackTransactionRepository.findByReference(reference)
                        .orElse(new PaystackTransaction());

                transaction.setReference(reference);
                transaction.setAmount(amount);
                transaction.setStatus(status);
        
                paystackTransactionRepository.save(transaction);
                // Also calling the Verify transaction endpoint
                ResponseModel responseModel = paystackService.verifyTransaction(reference);
                if (responseModel.getStatusCode() == HttpStatus.OK.value()) {
                    log.info("Transaction with reference {} verified successfully", reference);
                } else {
                    log.warn("Transaction with reference {} could not be verified: {}", reference, responseModel.getMessage());
                }                
                
                log.info("Transaction with reference {} processed successfully", reference);
                return true;
            }
        } catch (Exception e) {
            log.error("Error processing Paystack webhook event: {}", e.getMessage());
        }
        return false;
    }
}
