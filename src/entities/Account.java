// Base class
// Abstract class-because it have 3 methods which have different defination in derived classes
package src.entities;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Account {

    private long accountNumber;
    private String password;
    public LocalDate accOpenDate;
    public String accType;
    public LocalDate lastWithdrawdate;
    // Balance
    protected double balance;
    public String balanceType;

    // Default constructor
    public Account() {
    }

    /**
     * Parameter constructor to intialize Account with a custom Account
     * Number ,username,password,age,mobileno,Account opened date.
     */
    public Account(long accountNumber, String password,
            LocalDate accOpenDate, String accType, String balType) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.lastWithdrawdate = accOpenDate;
        this.accOpenDate = accOpenDate;
        this.accType = accType;
        this.balanceType = balType;
        balance = 0;
    }

    // Getter methods and setter methods
    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = Math.round(balance);
    }

    public LocalDate getdate() {
        return this.accOpenDate;
    }

    public long getAccountNumber() {
        return this.accountNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean checkPassword(String password) {
        if (this.password.equals(password)) {
            return true;
        }
        return false;
    }

    LocalDate date;

    public void setLastWithdrawDate() {
        this.lastWithdrawdate = LocalDate.now();
    }

    // this arraylist is used to store last transactions
    public ArrayList<MiniStatement> miniStatements = new ArrayList<MiniStatement>();

    // the below methods have different definations in the derived
    // classes(currentAccount class and savingsAccount class)
    public abstract void deposit(CIF cifs[], int cifindex, double amount, LocalDate lastwithdrawDate);

    public abstract void withdraw(CIF cifs[], int cifindex, double amount, LocalDate lastwithdrawDate);

    // this function is used to check the balance amount
    public void balanceEnquiry() {
        System.out.println("Balance Amount     : "+Math.round(balance));

    }

}
