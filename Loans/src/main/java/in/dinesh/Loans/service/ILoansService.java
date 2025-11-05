package in.dinesh.Loans.service;

import in.dinesh.Loans.dto.LoansDto;

public interface ILoansService {

    void createLoan(String mobileNumber);
    LoansDto fetchLoan(String mobileNumber);
    boolean updateLoan(LoansDto loansDto);
    boolean deleteLoan(String mobileNumber);
}
