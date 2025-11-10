package in.dinesh.Accounts.controller;

import in.dinesh.Accounts.constants.AccountsConstants;
import in.dinesh.Accounts.dto.AccountsContactInfoDto;
import in.dinesh.Accounts.dto.CustomerDto;
import in.dinesh.Accounts.dto.ResponseDto;
import in.dinesh.Accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Accounts Controller",
        description = "CRUD REST API's to Create, update,fetch,delete")
@RestController
@RequestMapping(path="/api" , produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private final IAccountsService accountsService;

    public AccountsController(IAccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @Value("${build.version}")

    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;


    @Operation(summary = "Create a new account", description = "REST API's to create new Customer & Account")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch account details", description = "REST API's to fetch account details")
    @ApiResponse(responseCode = "200", description = "Account details fetched successfully")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam("mobileNumber")
                                                               @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {

        CustomerDto customerDto = accountsService.fetchAccounts(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);

    }

    @Operation(summary = "Update account details", description = "REST API's to update account details")
    @ApiResponses(
            value = {
                    @ApiResponse( responseCode = "200", description = "Account details updated successfully"),
                    @ApiResponse( responseCode = "500", description = "Account details update failed")
            }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(summary = "Delete account details", description = "REST API's to delete account details")
    @ApiResponses(
            value = {
                    @ApiResponse( responseCode = "200", description = "Account details deleted successfully"),
                    @ApiResponse( responseCode = "500", description = "An Error occurred. Please try again or contact Dev team.")
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "$|[0-9]{10}", message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);

        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    @Operation(summary = "Fetch build info", description = "REST API's to fetch build info")
    @ApiResponses(
            value = {
                    @ApiResponse( responseCode = "200", description = "Build info fetched successfully"),
                    @ApiResponse( responseCode = "500", description = "An Error occurred. Please try again or contact Dev team.")
            }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(summary = "Fetch Java Version", description = "Get Java Version details that is installed into accounts microservices...")
    @ApiResponses(
            value = {
                    @ApiResponse( responseCode = "200", description = "Build info fetched successfully"),
                    @ApiResponse( responseCode = "500", description = "An Error occurred. Please try again or contact Dev team.")
            }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }


    @Operation(summary = "Fetch Contact Info", description = "Get Contact Info details that is installed into accounts microservices...")
    @ApiResponses(
            value = {
                    @ApiResponse( responseCode = "200", description = "Build info fetched successfully"),
                    @ApiResponse( responseCode = "500", description = "An Error occurred. Please try again or contact Dev team.")
            }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }



}
