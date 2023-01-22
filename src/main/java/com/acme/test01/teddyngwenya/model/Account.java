package com.acme.test01.teddyngwenya.model;

import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(exclude = {"balance","customerNumber"})
public abstract sealed class Account permits CurrentAccount, SavingsAccount{
    private final long id;
    private long balance;
    private final String customerNumber;

    //Improvement: ID will be auto-generated
    protected Account(long id, final String customerNumber,final long balance) {
        this.id = id;
        this.balance = balance;
        this.customerNumber = customerNumber;
    }

    //validates withdrawal rules for account type
    // this is to prevent subclasses calling the setBalance() without proper validation hence the omitted setter method for
    // the balance field
    protected abstract void validateWithdrawal(long amountToWithdraw) throws WithdrawalAmountTooLargeException;

    public void withdraw(long amountToWithdraw) throws WithdrawalAmountTooLargeException {
        validateWithdrawal(amountToWithdraw);
        balance -= amountToWithdraw;
    }
    public void deposit(long amountToDeposit){
        balance += amountToDeposit;
    }

}
