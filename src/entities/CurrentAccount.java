package src.entities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Checking Account
public class CurrentAccount extends Account {

    // Default Transaction Fee
    private double fee;

    // default constructor
    public CurrentAccount() {
        super();
    }

    /**
     * Parameter constructor to intialize Current Account with a custom Account
     * Number and a Customer Transaction Fee.
     */
    public CurrentAccount(long accountNumber, String password,
            LocalDate accOpenDate, String accType, String balType) {
        super(accountNumber, password, accOpenDate, accType, balType);
        this.fee = 0.50;
    }

    // getter method
    public double getFees() {
        return this.fee;
    }

    // this function is used to return fee based on amount
    public double getFee(double amount) {
        if (amount > 25000 && amount < 100000) {
            fee = 20;
        } else if (amount >= 100000) {
            fee = 100;
        }
        return fee;
    }

    // This function is used to deposit money to a custom Account
    public void deposit(CIF cifs[], int cifindex, double amount, LocalDate lastwithdrawDate) {

        // First Check amount
        if (amount > 0) {
            balance += amount;
            System.out.println("Hi " + cifs[cifindex].getUsername());
            System.out.printf("Amount %.2f deposited%n", amount);
            // System.out.printf("Current Balance is: %.2f%n", balance);
        } else
            System.out.println("A negative amount cannot be deposited");
    }

    // this function is used to withdraw money from a custom Account
    public void withdraw(CIF cifs[], int cifindex, double amount, LocalDate lastwithdrawDate) {
        // Same check
        if (amount > 0) {
            fee = getFee(amount);
            // Check sufficient balance
            if ((amount + fee) <= balance) {
                System.out.println("Hi " + cifs[cifindex].getUsername());
                System.out.printf("Amount of %.2f withdrawn from Account%n", amount);
                balance -= amount;
                balance -= fee;
                System.out.printf("Fee of %.2f applied%n", fee);
                System.out.printf("Current Balance is: %.2f%n", balance);
            } else if ((amount + fee) > balance)
                System.out.println("You have not sufficient Balance:\n");
            else
                System.out.println("Negative amount cannot be withdrawn!");

        } else
            System.out.println("entered amount must be greater than : 0");
    }

    // this function is used to count days between lastWithdrawDate and present date
    public int countdays(LocalDate lastwithdrawDate) {
        long days = 0;
        if (!lastwithdrawDate.equals(LocalDate.now())) {
            days = lastWithdrawdate.until(LocalDate.now(), ChronoUnit.DAYS);
        }
        return (int) days;
    }

    // this function is used to display Notifications like low minimum Balance
    public void displayNotification(Account accounts[], int index) {
        if (accounts[index].balanceType.equals("MinimumBalanceAccount")) {
            if (accounts[index].balance < 5000) {
                int days = countdays(accounts[index].lastWithdrawdate);
                if (days > 330 && days < 365) {
                    System.out.println("Your Account is under minimum balance from past no.of days : " + days + "\n");
                }
                if (days == 365) {
                    accounts[index].balance -= 150;
                    accounts[index].miniStatements
                            .add(new MiniStatement("MinimumBalancepenalty", LocalDate.now(), -150));
                    System.out.println("debited penalty amount from your account...\n");
                    lastWithdrawdate = getdate();
                }
            }
        }
    }

}
