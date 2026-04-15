package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts API", description = "API for managing customer accounts")
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsController.class);
    private static final String MOBILE_NUMBER_REGEX = "^[0-9]{10}$";

    private final IAccountService iAccountService;
    private final Environment environment;
    private final AccountsContactInfoDto accountsContactInfoDto;

    public AccountsController(IAccountService iAccountService,
                              Environment environment,
                              AccountsContactInfoDto accountsContactInfoDto) {
        this.iAccountService = iAccountService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    @Value("${build.version}")
    private String buildVersion;


    @Operation(summary = "Get a new account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        iAccountService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch account details by mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(@Pattern(regexp = MOBILE_NUMBER_REGEX, message = "Mobile number must be exactly 10 digits")
                                                    @RequestParam String mobileNumber) {
        var customerDto = iAccountService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    @Operation(summary = "Update account details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountService.updateAccountDetails(customerDto);
        if (!isUpdated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(AccountsConstants.STATUS_404, AccountsConstants.MESSAGE_404));
        }

        return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }


    @Operation(summary = "Delete account by mobile number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@Pattern(regexp = MOBILE_NUMBER_REGEX, message = "Mobile number must be exactly 10 digits")
                                                     @RequestParam String mobileNumber) {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if (!isDeleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDto(AccountsConstants.STATUS_404, AccountsConstants.MESSAGE_404));
        }

        return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
    }


    //usage with @value annotation
    @Retry(name = "buildInfo", fallbackMethod = "getBuildInfoFallback")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    public ResponseEntity<String> getBuildInfoFallback(Throwable throwable) {
        LOGGER.debug("getBuildInfoFallback() Error occurred while fetching build info: {}", throwable.getMessage());
        return ResponseEntity.internalServerError().body("Error occurred while fetching build info: " + throwable.getMessage());
    }

    //usage with environment interface
    @GetMapping("/java-version")
    @RateLimiter(name = "javaVersion", fallbackMethod = "getJavaVersionFallback")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("java.home"));
    }

    public ResponseEntity<String> getJavaVersionFallback(Throwable throwable) {
        LOGGER.debug("getJavaVersionFallback() Error occurred while fetching java version: {}", throwable.getMessage());
        return ResponseEntity.ok("JAVA 21");
    }
    //usage with @configurationProperties annotation
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(accountsContactInfoDto);
    }
}
