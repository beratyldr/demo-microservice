package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.feigndto.CardDto;
import com.eazybytes.accounts.dto.feigndto.LoansDto;
import com.eazybytes.accounts.service.CustomerService;
import com.eazybytes.accounts.service.IAccountService;
import com.eazybytes.accounts.service.clients.CardsFeignClient;
import com.eazybytes.accounts.service.clients.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    private final IAccountService iAccountService;

    /**
     * Retrieves customer details based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @return A {@link CustomerDetailsDto} containing the customer's details.
     */
    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) {
        CustomerDto customerDto = iAccountService.fetchAccount(mobileNumber);
        ResponseEntity<CardDto> cardDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClient.fetchLoansDetails(mobileNumber);

        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        customerDetailsDto.setCardDto(cardDtoResponseEntity.getBody());
        customerDetailsDto.setLoansDto(loansDtoResponseEntity.getBody());
        customerDetailsDto.setCustomerDto(customerDto);
        return customerDetailsDto;
    }
}
