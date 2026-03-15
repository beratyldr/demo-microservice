package com.eazybytes.accounts.dto;

import com.eazybytes.accounts.dto.feigndto.CardDto;
import com.eazybytes.accounts.dto.feigndto.LoansDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Data Transfer Object for Customer Details,Loans, Cards including account information.")
public class CustomerDetailsDto {

    @Schema(description = "Details about the customer including personal information.")
    private CustomerDto customerDto;

    @Schema(description = "Details about the loans associated with the customer.")
    private LoansDto loansDto;

    @Schema(description = "Details about the cards associated with the customer.")
    private CardDto cardDto;
}
