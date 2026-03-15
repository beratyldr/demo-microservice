package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    /**
     * Creates a new account for the given customer.
     *
     * @param customerDto the customer details required to create the account
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        if (customerDto == null) {
            throw new IllegalArgumentException("Customer details cannot be null");
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        validateCustomerSave(customerDto);
        Customer savedCustomer = customerRepository.save(customer);

        accountRepository.save(fillAccountDetails(savedCustomer));
    }

    /**
     * Fetches the account details for a customer based on their mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose account details are to be fetched
     * @return a CustomerDto containing the customer's details and associated account information
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new EntityNotFoundException("No customer found with mobile number: " + mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new EntityNotFoundException("No account found for customer with mobile number: " + mobileNumber));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    
    /**
     * Updates the account details of an existing customer.
     *
     * @param customerDto the customer details to be updated
     * @return true if the account was successfully updated, false otherwise
     */
    @Override
    public boolean updateAccountDetails(CustomerDto customerDto) {
        if (customerDto == null || customerDto.getMobileNumber() == null) {
            throw new IllegalArgumentException("Customer or mobile number cannot be null");
        }
        Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .orElseThrow(() -> new EntityNotFoundException("No customer found with mobile number: " + customerDto.getMobileNumber()));

        Account account = accountRepository.findByAccountNumber(customerDto.getAccountsDto().getAccountNumber())
                .orElseThrow(() -> new EntityNotFoundException("No account found for customer with ID: " + customer.getCustomerId()));

        Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto, customer);
        Account updatedAccount = AccountsMapper.mapToAccount(customerDto.getAccountsDto(), account);

        customerRepository.save(updatedCustomer);
        accountRepository.save(updatedAccount);

        return Boolean.TRUE;
    }

    /**
     * Deletes the account associated with the provided mobile number.
     *
     * @param mobileNumber the mobile number of the customer whose account needs to be deleted
     * @return true if the account was successfully deleted, false otherwise
     */
    @Override
    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new EntityNotFoundException("No customer found with mobile number: " + mobileNumber));

        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    private void validateCustomerSave(CustomerDto customerDto) {
        Optional<Customer> byEmail = customerRepository.findByEmail(customerDto.getEmail());

        byEmail.ifPresent(existingCustomer -> {
            throw new IllegalArgumentException("Failed to save customer details for email: " + customerDto.getEmail());
        });
        Optional<Customer> byMobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        byMobileNumber.ifPresent(existingCustomer -> {
            throw new IllegalArgumentException("Failed to save customer details for mobile number: " + customerDto.getMobileNumber());
        });
    }


    /**
     * Populates account details for a given saved customer. This method uses
     * static data for the account type and branch address, defined in
     * {@link AccountsConstants}.
     *
     * @param savedCustomer the saved customer object for whom the account is created
     * @return a populated account entity
     */
    private Account fillAccountDetails(Customer savedCustomer) {
        Account account = new Account();
        account.setCustomerId(savedCustomer.getCustomerId());
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        return account;
    }

    private Long generateAccountNumber() {
        return 1000000000L + new Random().nextInt(900000000);
    }


}
