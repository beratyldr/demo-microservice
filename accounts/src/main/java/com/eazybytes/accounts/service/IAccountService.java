package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountService {

    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the customer details required to create the account
     */
    void createAccount(CustomerDto customerDto);


    /**
     * Fetches the account details for a customer based on their mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose account details are to be fetched
     * @return a CustomerDto containing the customer's details and associated account information
     */
    CustomerDto fetchAccount(String mobileNumber);


    /**
     * Updates the account details of an existing customer.
     *
     * @param customerDto the customer details to be updated
     * @return true if the account was successfully updated, false otherwise
     */
    boolean updateAccountDetails(CustomerDto customerDto);


    /**
     * Deletes the account associated with the provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose account needs to be deleted
     * @return true if the account was successfully deleted, false otherwise
     */
    boolean deleteAccount(String mobileNumber);
    
}
