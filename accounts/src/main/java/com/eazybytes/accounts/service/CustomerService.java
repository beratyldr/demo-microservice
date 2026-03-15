package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    /**
     * Retrieves customer details based on the provided mobile number.
     *
     * @param mobileNumber The mobile number associated with the customer.
     * @return A {@link CustomerDetailsDto} containing the customer's details.
     */
    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);
}
