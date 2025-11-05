package in.dinesh.Accounts.service.impl;

import in.dinesh.Accounts.constants.AccountsConstants;
import in.dinesh.Accounts.dto.AccountsDto;
import in.dinesh.Accounts.dto.CustomerDto;
import in.dinesh.Accounts.entity.Accounts;
import in.dinesh.Accounts.entity.Customer;
import in.dinesh.Accounts.exception.CustomerAlreadyExistException;
import in.dinesh.Accounts.exception.ResourceNotFoundException;
import in.dinesh.Accounts.mapper.AccountsMapper;
import in.dinesh.Accounts.mapper.CustomerMapper;
import in.dinesh.Accounts.repository.AccountsRepository;
import in.dinesh.Accounts.repository.CustomerRepository;
import in.dinesh.Accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;


    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        customerRepository.findByMobileNumber(customerDto.getMobileNumber()).ifPresent(c -> {
            throw new CustomerAlreadyExistException("Customer already exist with mobile number: "
                    + customerDto.getMobileNumber());
        });

        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }

    @Override
    public CustomerDto fetchAccounts(String mobileNumber) {

       Customer customer= customerRepository.findByMobileNumber(mobileNumber)
               .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

       Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
               .orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();

        if(accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("Accounts", "accountNumber", accountsDto.getAccountNumber().toString()));

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "customerId", customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
           customerRepository.save(customer);

           isUpdated = true;

        }

        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
