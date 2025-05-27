package com.ledger.service;

import com.ledger.model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class BankTest {
    private Bank bank;
    private BankAccount account1;
    private BankAccount account2;
    private BankAccount account3;

    @BeforeEach
    void setUp() {
        account1 = new BankAccount.Builder()
                .withAccountNumber("123")
                .withBalance(1000)
                .withAccountHolder("John Snow")
                .build();
        account2 = new BankAccount.Builder()
                .withAccountNumber("456")
                .withBalance(2000)
                .withAccountHolder("Edward Stark")
                .build();
        account3 = new BankAccount.Builder()
                .withAccountNumber("789")
                .withBalance(3000)
                .withAccountHolder("Daenerys Targaryen")
                .build();
        bank = new Bank(new ArrayList<>());
        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);
    }

    @Test
    void deposit999ToAccount1IncreasesBalanceTo1999() {
        bank.deposit("123", 999);
        assertEquals(1999, account1.getBalance());
    }
    @Test
    void deposit999_99ToAccount3IncreasesBalanceTo1999_99() {
        bank.deposit("789", 999.99);
        assertEquals(3999.99, account3.getBalance());
    }
    @Test
    void getBankAccountByNumberReturnsCorrectAccount() {
        assertEquals(account1, bank.getBankAccountByNumber("123"));
        assertEquals(account2, bank.getBankAccountByNumber("456"));
    }

    @Test
    void withdrawFromExistingAccountDecreasesBalance() {
        assertEquals(1000, account1.getBalance());
        bank.withdraw(account1.getAccountNumber(), 500);
        assertEquals(500, account1.getBalance());
    }

    @Test
    void withdrawFromNonExistingAccountDoesNotChangeBalance() {
        bank.withdraw("789", 500);
        assertEquals(1000, account1.getBalance());
        assertEquals(2000, account2.getBalance());
    }

    @Test
    void withdrawMoreThanBalanceReturnsFalse() {
        assertFalse(bank.withdraw("123", 1500));
    }

    @Test
    void depositToExistingAccountIncreasesBalance() {
        bank.deposit("456", 500);
        assertEquals(2500, account2.getBalance());
    }

    @Test
    void depositToNonExistingAccountDoesNotChangeBalance() {
        bank.deposit("789", 500);
        assertEquals(1000, account1.getBalance());
        assertEquals(2000, account2.getBalance());
    }

    @Test
    void depositNegativeAmountReturnsFalse() {
        assertFalse(bank.deposit("123", -500));
    }

    @Test
    void transferFundsBetweenAccountsDecreasesFromAccount1AndIncreasesAccount2() {
        bank.transferFounds(account1, account2, 500);
        assertEquals(500, account1.getBalance());
        assertEquals(2500, account2.getBalance());
    }

    @Test
    void transferFundsBetweenAccountsDecreasesFromAccount2AndIncreasesAccount1() {
        bank.transferFounds(account2, account1, 1000);
        assertEquals(2000, account1.getBalance());
        assertEquals(1000, account2.getBalance());
    }

    @Test
    void transferFundsBetweenAccountsDecreasesFromAccount1AndIncreasesAccount3() {
        bank.transferFounds(account1, account3, 500);
        assertEquals(500, account1.getBalance());
        assertEquals(3500, account3.getBalance());
    }

    @Test
    void transferFundsBetweenAccountsDecreasesFromAccount3AndIncreasesAccount2() {
        bank.transferFounds(account3, account2, 2500);
        assertEquals(500, account3.getBalance());
        assertEquals(4500, account2.getBalance());
    }
    @Test
    void printBankTransactions(){
        bank.printLedger();
    }
}
