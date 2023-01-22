package com.acme.test01.teddyngwenya.service;

import com.acme.test01.teddyngwenya.exception.AccountNotFoundException;
import com.acme.test01.teddyngwenya.exception.SavingsAccountOpeningBalanceException;
import com.acme.test01.teddyngwenya.exception.WithdrawalAmountTooLargeException;
import com.acme.test01.teddyngwenya.model.Account;
import com.acme.test01.teddyngwenya.repository.AccountRepository;
import com.acme.test01.teddyngwenya.repository.SystemDB;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceImplTest {
    private AccountService accountService;
    private final AccountRepository accountRepository = SystemDB.getInstance();

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    @Order(1)
    void testOpenSavingsAccount() {
        accountService.openSavingsAccount(5L, 2000);
        Account account = accountRepository.findById(5L).orElseThrow();
        assertEquals(2000, account.getBalance());
    }

    @Test
    @Order(2)
    void testOpenSavingsAccountWithNotEnoughBalance() {
        assertThrows(SavingsAccountOpeningBalanceException.class, () -> accountService.openSavingsAccount(5L, 1000));
    }

    @Test
    @Order(3)
    public void testOpenCurrentAccount() {
        accountService.openCurrentAccount(6L);
        Account account = accountRepository.findById(6L).orElseThrow();
        assertEquals(0, account.getBalance());
    }

    @Test
    @Order(4)
    public void testWithdrawWithSavingsAccount() throws WithdrawalAmountTooLargeException {
        accountService.withdraw(2L, 2000);
        assertEquals(3000, accountRepository.findById(2L).orElseThrow().getBalance());
    }

    @Test
    @Order(5)
    public void testWithdrawWithSavingsAccountNotEnoughBalance() {
        assertThrows(WithdrawalAmountTooLargeException.class, () -> accountService.withdraw(1L, 6000));
    }

    @Test
    @Order(6)
    public void testWithdrawWithCurrentAccount() throws WithdrawalAmountTooLargeException {
        accountService.withdraw(3L, 2000);
        assertEquals(-1000, accountRepository.findById(3L).orElseThrow().getBalance());
    }

    @Test
    @Order(7)
    public void testWithdrawWithCurrentAccountNotEnoughBalance() {
        assertThrows(WithdrawalAmountTooLargeException.class, () -> accountService.withdraw(2L, 200000));
    }

    @Test
    @Order(8)
    public void testDepositWithSavingsAccount() {
        accountService.deposit(1L, 2000);
        assertEquals(4000, accountRepository.findById(1L).orElseThrow().getBalance());
    }

    @Test
    @Order(9)
    public void testDepositWithCurrentAccount() {
        accountService.deposit(3L, 2000);
        assertEquals(1000, accountRepository.findById(3L).orElseThrow().getBalance());
    }

    @Test
    @Order(10)
    public void testWithdrawWithNonExistingAccount() {
        assertThrows(AccountNotFoundException.class, () -> accountService.withdraw(7L, 2000));
    }

    @Test
    @Order(11)
    public void testDepositWithNonExistingAccount() {
        assertThrows(AccountNotFoundException.class, () -> accountService.deposit(7L, 2000));
    }

}