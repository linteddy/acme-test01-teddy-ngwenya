package com.acme.test01.teddyngwenya.service;


import com.acme.test01.teddyngwenya.exception.AccountNotFoundException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;

public interface AccountService {
    void openSavingsAccount(long accountId, long amountToDeposit);
    void openCurrentAccount(long accountId);
    void withdraw(long accountId, long amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException;

    void deposit(long accountId, long amountToDeposit)throws AccountNotFoundException;
}
