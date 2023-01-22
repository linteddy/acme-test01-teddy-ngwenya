package com.acme.test01.teddyngwenya.model;

import com.acme.test01.teddyngwenya.exception.CurrentAccountOverdraftLimitException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;

public non-sealed class CurrentAccount extends Account {
    //improvement: Move the creation to a factory class inorder to configure the maximum overdraft limit in a db
    // or config file to update it during runtime
    public static final long MAXIMUM_OVERDRAFT_LIMIT = 100_000;
    long overdraftLimit;

    private CurrentAccount(final long id, final String customerNumber, final long balance, final long overdraftLimit) {
        super(id, customerNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public static CurrentAccount of(final long id, final String customerNumber,final long balance, final long overdraft) {
        //this can be moved into a factory class to allow the configuration of the limit without changing code
        if (overdraft > MAXIMUM_OVERDRAFT_LIMIT) {
            String errorMessage = String.format("Overdraft limit can not be more than %s", MAXIMUM_OVERDRAFT_LIMIT);
            throw new CurrentAccountOverdraftLimitException(errorMessage);
        }
        return new CurrentAccount(id, customerNumber, balance, overdraft);
    }

    @Override
    protected void validateWithdrawal(long amountToWithdraw) throws WithdrawalAmountTooLargeException {
        long maximumWithdrawalAmount = getBalance() + overdraftLimit;
        if(amountToWithdraw > maximumWithdrawalAmount){
            String errorMessage = String.format("Can not withdraw amount : %s, the maximum you can withdraw is %s",
                    amountToWithdraw, maximumWithdrawalAmount);
            throw new WithdrawalAmountTooLargeException(errorMessage);
        }
    }
}
