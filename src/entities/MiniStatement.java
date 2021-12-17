package src.entities;

import java.time.LocalDate;

//this class is used for MiniStatements
public class MiniStatement {
    public String transactionType;
    public LocalDate transactionDate;
    public double balance;
    public double fee;

    public MiniStatement(String transactionType, LocalDate transactioDate, double balance) {
        this.transactionDate = transactioDate;
        this.transactionType = transactionType;
        this.balance = balance;
    }

    public MiniStatement(String transactionType, LocalDate transactioDate, double balance, double fee) {
        this.transactionDate = transactioDate;
        this.transactionType = transactionType;
        this.balance = balance;
        this.fee = fee;
    }

}
