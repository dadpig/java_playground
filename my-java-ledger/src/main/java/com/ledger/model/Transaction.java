package com.ledger.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private String id;
    private BankAccount from;
    private BankAccount to;
    private double amount;

    private TransactionOperation operation;
    public enum TransactionOperation {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    };
    private LocalDateTime timestamp;

    private Transaction(BankAccount from, BankAccount to, double amount, TransactionOperation operation) {
        this.id = UUID.randomUUID().toString();
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.operation = operation;
    }


    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                ", operation=" + operation +
                ", timestamp=" + timestamp +
                '}';
    }



    public static class Builder{
        private BankAccount from;
        private BankAccount to;
        private double amount;

        private TransactionOperation operation;

        public Builder withOperation(TransactionOperation operation) {
            this.operation = operation;
            return this;
        }
        public Builder withFrom(BankAccount from) {
            this.from = from;
            return this;
        }

        public Builder withTo(BankAccount to) {
            this.to = to;
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() {
            return new Transaction(from, to, amount, operation);
        }
    }
}
