package com.ledger.service;

import com.ledger.model.BankAccount;
import com.ledger.model.Transaction;
import com.ledger.repository.Ledger;

import java.util.List;

public class Bank {
    private List<BankAccount> accounts;

    public Bank(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public BankAccount getBankAccountByNumber(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    public boolean withdraw(String accountNumber, double amount) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                BankAccount from = new BankAccount.Builder()
                        .withAccountNumber(account.getAccountNumber())
                        .withBalance(account.getBalance())
                        .withAccountHolder(account.getAccountHolder())
                        .build();
                if(account.getBalance() >= amount) {
                    account.withdraw(amount);
                    Ledger.addTransaction(from,account, amount, Transaction.TransactionOperation.WITHDRAW);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean deposit(String accountNumber, double amount) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                BankAccount from = new BankAccount.Builder()
                        .withAccountNumber(account.getAccountNumber())
                        .withBalance(account.getBalance())
                        .withAccountHolder(account.getAccountHolder())
                        .build();
                if(amount > 0) {
                    account.deposit(amount);
                    Ledger.addTransaction(from,account, amount, Transaction.TransactionOperation.DEPOSIT);
                    return true;
                }
            }
        }
        return false;
    }

    public  boolean transferFounds(BankAccount account1, BankAccount account2, double amount){
        if(account1.getBalance() >= amount){
            account1.withdraw(amount);
            account2.deposit(amount);
            Ledger.addTransaction(account1, account2, amount, Transaction.TransactionOperation.TRANSFER);
            return true;
        }
        return false;
    }

    public void printLedger(){
        Ledger.print();
    }
}


