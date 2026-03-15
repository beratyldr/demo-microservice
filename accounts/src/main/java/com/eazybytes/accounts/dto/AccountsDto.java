package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for Account details, including account number, type, and branch information.")
public class AccountsDto {

    @Schema(description = "Account number uniquely identifying the account.", example = "1234567890")
    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    private Long accountNumber;
    @Schema(description = "Type of the account (e.g., Savings, Checking).", example = "Savings")
    @NotEmpty(message = "Account type cannot be null or empty")
    private String accountType;
    @Schema(description = "Address of the branch associated with the account.", example = "123 Main Street, NY")
    @NotEmpty(message = "Branch address cannot be null or empty")
    private String branchAddress;

    
}
