package com.eazybytes.accounts.service.clients;

import com.eazybytes.accounts.dto.feigndto.CardDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFallback implements CardsFeignClient {

    @Override
    public ResponseEntity<CardDto> fetchCardDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
