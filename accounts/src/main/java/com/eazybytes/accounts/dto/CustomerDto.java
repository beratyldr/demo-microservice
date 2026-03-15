package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Data Transfer Object for Customer, containing customer's details such as name, email, mobile number, and account information.")
public class CustomerDto {

    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Schema(description = "Full name of the customer, must be between 3 and 50 characters.", example = "John Doe", required = true)
    private String name;

    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the customer in a valid format.", example = "john.doe@example.com", required = true)
    private String email;

    @NotEmpty(message = "Mobile number cannot be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be exactly 10 digits")
    @Schema(description = "Mobile number of the customer, must be exactly 10 digits.", example = "1234567890", required = true)
    private String mobileNumber;

    private AccountsDto accountsDto;


}
