package com.acme.test01.teddyngwenya.model;

import com.acme.test01.teddyngwenya.exception.CurrentAccountOverdraftLimitException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CurrentAccountTest {

    @Test
    @DisplayName("Current accounts can have an overdraft limit maximum of 100_000")
    void testMaximumOverdraftLimit() {
        Assertions.assertThrows(
                CurrentAccountOverdraftLimitException.class,
                () -> CurrentAccount.of(1L, "john", 5000, 1_200_000)
        );
    }

    @Test
    @DisplayName("no minimum deposit requirement")
    void testAccountCreationDeposit() {
        var account = CurrentAccount.of(1L, "john", 0, 30_000);
        assertNotNull(account);
    }

    @Test
    @DisplayName("cannot withdraw more than the (balance + overdraft limit) on a current account")
    void testCanNotWithdrawMoreThanBalanceAndOverdraft() {
        WithdrawalAmountTooLargeException exception = Assertions.assertThrows(WithdrawalAmountTooLargeException.class, () -> {
            var currentAccount = CurrentAccount.of(1L, "john", 5000, 25_000);
            currentAccount.withdraw(31_000);
        });
        assertEquals("Can not withdraw amount : 31000, the maximum you can withdraw is 30000", exception.getMessage())
        ;
    }

    @Test
    @DisplayName("balance is decreased by the amount withdrawn")
    void testBalanceDecreasedAfterWithdrawal() throws WithdrawalAmountTooLargeException {
        var currentAccount = CurrentAccount.of(1L, "john", 5000, 25_000);

        currentAccount.withdraw(4000);
        assertEquals(1000, currentAccount.getBalance());
    }

    @Test
    @DisplayName("balance can have a negative value after withdrawal")
    void testOverdraftWithdrawal() throws WithdrawalAmountTooLargeException {
        var currentAccount = CurrentAccount.of(1L, "john", 5000, 25_000);

        currentAccount.withdraw(10_000);
        assertEquals(-5000, currentAccount.getBalance());
    }

    @Test
    @DisplayName("balance is increased by the amount deposited")
    void testBalanceIncreaseAfterDeposit() {
        var currentAccount = CurrentAccount.of(1L, "john", 5000, 25_000);

        currentAccount.deposit(4000);
        assertEquals(9000, currentAccount.getBalance());
    }

}