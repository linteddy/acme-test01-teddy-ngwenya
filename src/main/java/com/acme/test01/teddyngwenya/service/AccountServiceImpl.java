package com.acme.test01.teddyngwenya.service;

import com.acme.test01.teddyngwenya.exception.AccountNotFoundException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.teddyngwenya.model.Account;
import com.acme.test01.teddyngwenya.model.CurrentAccount;
import com.acme.test01.teddyngwenya.model.SavingsAccount;
import com.acme.test01.teddyngwenya.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
    private  final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    //improvement: make this method accept Savings account instance without an id,
    // auto-generate id and return it
    @Override
    public void openSavingsAccount(long accountId, long amountToDeposit) {
        accountRepository.save(SavingsAccount.of(accountId,"customer"+accountId, amountToDeposit));
    }

    //improvement: make this method accept Current account instance without an id,
    // auto-generate id and return it
    @Override
    public void openCurrentAccount(long accountId) {
        accountRepository.save(CurrentAccount.of(accountId,"customer"+accountId, 0,100_000));
    }

    @Override
    public void withdraw(final long accountId, final long amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->
                        new AccountNotFoundException(String.format("Account with Id %d does not exist",accountId)));
        account.withdraw(amountToWithdraw);
    }

    @Override
    public void deposit(long accountId, long amountToDeposit) throws AccountNotFoundException {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()->
                        new AccountNotFoundException(String.format("Account with Id %d does not exist",accountId)));
        account.deposit(amountToDeposit);
    }
}
