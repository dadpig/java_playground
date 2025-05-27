package com.ledger.repository;

import com.ledger.model.BankAccount;
import com.ledger.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Ledger {

    private static List<Transaction> transactions = new ArrayList<>();

    public  static void addTransaction(BankAccount from, BankAccount to, double amount, Transaction.TransactionOperation operation) {
        transactions.add(new Transaction.Builder()
                .withFrom(from)
                .withTo(to)
                .withAmount(amount)
                .withOperation(operation)
                .build());
    }

    public static List<Transaction> getTransactions() {
        return transactions;
    }

    public static void print(){
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public String toString() {
        return "Ledger{" +
                "transactions=" + transactions +
                '}';
    }

}
