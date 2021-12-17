package src.entities;

import java.time.LocalDate;
// Savings Account child class
// has an interest rate
// a method to apply interest - profit 
import java.time.temporal.ChronoUnit;

public class SavingsAccount extends Account {

    // interest rate
    private double interestRate;

    // default constructor
    public SavingsAccount() {
        super();
    }

    /**
     * Parameter constructor to intialize Savings Account with a custom Account
     * Number and an interest Fee.
     */
    public SavingsAccount(long accountNumber, String password,
            LocalDate accOpenDate, String accType, String balType) {
        super(accountNumber, password, accOpenDate, accType, balType);
        this.interestRate = 4;
    }

    // getter function
    public double getInterestRate() {
        return this.interestRate;
    }

    // setter function
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // This function is used to calculate the interest amount
    public double calcInterest(CIF cifs[], int cifindex, Account accounts[], int index, LocalDate lastwithdrawDate) {
        if (cifs[cifindex].getAge() > 50)
            interestRate += 0.50;
        long days = countdays(lastwithdrawDate);
        if (days != 0) {
            double bal = accounts[index].getBalance();
            bal = (bal * days * 4) / 36500;
            return bal;
        }
        return 0;
    }

    //this function is used to count days between lastWithdrawDate and present date
    public long countdays(LocalDate lastwithdrawDate) {
        if (!lastwithdrawDate.equals(LocalDate.now())) {
            System.out.println(lastwithdrawDate + " to " + LocalDate.now()+" interest is added.");
            long days = lastwithdrawDate.until(LocalDate.now(), ChronoUnit.DAYS);
            return days;
        }
        return 0;
    }

    // This function is used to calculate the interest amount
    public double calcInterest(CIF cifs[], int cifindex) {
        if (cifs[cifindex].getAge() > 50)
            interestRate += 0.50;
        double bal = (balance * interestRate) / 100;
        return bal;
    }

    // this function is used to display interest Amount
    public void applyInterest(CIF cifs[], int cifindex) {
        double interest = calcInterest(cifs, cifindex);
        System.out.printf("Interest amount %.2f can be given per year%n", interest);

    }

    // this function is used to deposit amount in Account
    public void deposit(CIF cifs[], int cifindex, double amount, LocalDate lastwithdrawDate) {
        // First Check amount
        if (amount > 0) {
            System.out.println("Hi " + cifs[cifindex].getUsername());
            balance += amount;
            System.out.printf("Amount %.2f deposited%n", amount);
            //System.out.printf("Current Balance is: %.2f%n", balance);
            setLastWithdrawDate();
        } else
            System.out.println("A negative amount cannot be deposited");
    }

    //this function is used to display Notifications like low minimum Balance
    public void displayNotifications(Account accounts[], int index) {
        if (accounts[index].balanceType.equals("MinimumBalanceAccount")) {
            if (accounts[index].balance < 2000) {
                long days = countdays(accounts[index].lastWithdrawdate);
                if (days > 330 && days < 365) {
                    System.out.println("Your Account is under minimum balance from past no.of days : " + days + "\n");
                }
                if (days == 365) {
                    accounts[index].balance -= 100;
                    accounts[index].miniStatements
                            .add(new MiniStatement("MinimumBalancepenalty", LocalDate.now(), -100));
                    System.out.println("debited penalty amount from your account...\n");
                    setLastWithdrawDate();
                }
            }
        }
    }

    // this function is used to withdraw amount from Account
    public void withdraw(CIF cifs[], int cifindex, double amount, LocalDate lastwithdrawDate) {
        // Same check
        if (amount > 0) {
            // Check sufficient balance
            if ((amount) <= balance) {
                System.out.println("Hi " + cifs[cifindex].getUsername());
                System.out.printf("Amount of %.2f withdrawn from Account%n", amount);
                balance -= amount;
                System.out.printf("Current Balance is: %.2f%n", balance);
                lastWithdrawdate = LocalDate.now();
            }
        } else if ((amount) > balance)
            System.out.println("You have not sufficient Balance:\n");
        else
            System.out.println("Negative amount cannot be withdrawn!");
    }
}
