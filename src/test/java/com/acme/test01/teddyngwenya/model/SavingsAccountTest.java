package com.acme.test01.teddyngwenya.model;

import com.acme.test01.teddyngwenya.exception.SavingsAccountOpeningBalanceException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SavingsAccountTest {

    //Unit Test Naming Conventions will be determined by company standards
    //Convention used here : testFeatureBeingTested
    @Test
    @DisplayName("must have a minimum balance of 2000 at all times")
    void testWithdrawalAmountTooLarge() {
        Assertions.assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            var account = SavingsAccount.of(1L, "john", 2000L);
            account.withdraw(1);
        });
    }

    @Test
    @DisplayName("Savings account’s balance is decreased by the amount withdrawn")
    void testDecreasingOfBalanceByAmountWithdrawn() throws WithdrawalAmountTooLargeException {
        var account = SavingsAccount.of(1L, "john", 5000L);
        account.withdraw(2000);
        assertEquals(3000, account.getBalance());
    }

    @Test
    @DisplayName("Savings account can only be opened if you deposit at least 2000 in it")
    void testOpeningBalanceOfAtLeast2000() {
        Assertions.assertThrows(
                SavingsAccountOpeningBalanceException.class, () -> SavingsAccount.of(1L, "john", 1999L)
        );

    }

    @Test
    @DisplayName(("Savings account’s balance is increased by the amount deposited"))
    void testDeposit() {
        var account = SavingsAccount.of(1L, "john", 2001L);
        account.deposit(2000);
        assertEquals(4001, account.getBalance());
    }
}