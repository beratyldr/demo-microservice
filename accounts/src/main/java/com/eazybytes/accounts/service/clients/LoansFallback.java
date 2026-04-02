package com.eazybytes.accounts.service.clients;

import com.eazybytes.accounts.dto.feigndto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFallback implements LoansFeignClient {
    @Override
    public ResponseEntity<LoansDto> fetchLoansDetails(String mobileNumber, String correlationId) {
        return null;
    }
}
