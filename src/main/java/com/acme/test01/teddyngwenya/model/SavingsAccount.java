package com.acme.test01.teddyngwenya.model;

import com.acme.test01.teddyngwenya.exception.SavingsAccountOpeningBalanceException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;

public non-sealed class SavingsAccount extends Account{
    //improvement: Move the creation to a factory class inorder to configure the minimum balance in a db
    // or config file to update it during runtime
    public static final long MINIMUM_SAVINGS_BALANCE = 2000;

    private SavingsAccount(final long id, final String customerNumber, final long balance) {
        super(id, customerNumber, balance);

    }

    public static SavingsAccount of(final long id, final String customerNumber, final long balance){
        if(balance < MINIMUM_SAVINGS_BALANCE){
            throw new SavingsAccountOpeningBalanceException("Opening balance can not be less than 2000");
        }
        return new SavingsAccount(id, customerNumber, balance);
    }

    @Override
    protected void validateWithdrawal(long amountToWithdraw) throws WithdrawalAmountTooLargeException {
        if(getBalance() - amountToWithdraw < MINIMUM_SAVINGS_BALANCE){
            String errorMessage = String.format("Can not withdraw amount : %s, your balance is %s", amountToWithdraw, getBalance());
            throw new WithdrawalAmountTooLargeException(errorMessage);
        }
    }
}
