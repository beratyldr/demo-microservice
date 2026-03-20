package com.eazybytes.accounts.service.clients;

import com.eazybytes.accounts.dto.feigndto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cards")
public interface CardsFeignClient {
    @GetMapping(value = "/api/fetch",consumes = "application/json")
    ResponseEntity<CardDto> fetchCardDetails(@RequestParam String mobileNumber, @RequestHeader("eazybank-correlation-id") String correlationId);
}
