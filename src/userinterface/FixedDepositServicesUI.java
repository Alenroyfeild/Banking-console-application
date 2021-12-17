package src.userinterface;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import src.entities.Account;
import src.entities.FixedDeposit;
import src.services.FixedDepositServices;
import src.utils.LoginUtilities;

public class FixedDepositServicesUI {
    FixedDepositServices fds = new FixedDepositServices();

    public void applyFDAccountUI(FixedDeposit fd[], Account accounts[], long accountNumber, int index, int findex,
            double amount,
            Scanner keyboard) {
        if (findex >= 0) {
            fds.applyFDAccount(fd, accounts, accountNumber, index, findex, amount, keyboard);
            System.out.println("Fixed Deposit Account created successfully ...\n");
            getFDBalances(fd, findex, accounts, index);
        } else
            LoginUtilities.print("invalid account number...!");

    }

    // this function is used to display interest details based on amount
    public void displayFDInterestDetails() {
        System.out.println("Fixed Deposit interest is based on duration ");
        System.out.println("    Months         interest rate ");
        System.out.println("   1  to  6           6.5%      ");
        System.out.println("   6  to  12          7.50%   ");
        System.out.println("   12 to  24          8.25%   ");
        System.out.println("   above 24           8.50%      ");
    }

    // this function is used to Display custom Fixed Deposit Account Balances
    public void getFDBalances(FixedDeposit fd[], int findex, Account accounts[], int index) {
        System.out.format("Fixed Deposit Amount     : %.2f\n", fd[findex].fdAmount);
        System.out.format("Interest Rate            : %.2f\n", fd[findex].fdRate);
        System.out.printf("Account remaining Balance: %.2f\n", accounts[index].getBalance());
    }

    public int getMons(Scanner keyboard) {
        int mons = 0;
        do {
            try {
                displayFDInterestDetails();
                mons = Integer.parseInt(keyboard.next());
            } catch (Exception e) {
                System.out.println("entered months is not a valid integer");
            }
        } while (mons == 0);
        return mons;
    }

    // this function is used to get no of months from user for FD duration
    public int[] getMonths(Scanner keyboard) {
        int ref = 0, mons;
        int a[] = new int[2];
        double days;
        do {
            mons = getMons(keyboard);
            if (mons >= 1) {
                days = (mons * 365 / 12);
                a[0] = mons;
                a[1] = (int) Math.round(days);
                ref = 1;
                return a;
            } else {
                LoginUtilities.print("months must must be greater than 0..");
            }
        } while (ref == 0);
        return a;
    }

    // this function is used to get Available amount in Fixed deposit Account
    public double getAmount(Account accounts[], int index, Scanner keyboard) {
        int x = 1;
        do {
            System.out.print("Enter the amount to deposit in FD Account : ");
            double amt = LoginUtilities.getAmount(keyboard);
            if (accounts[index].getBalance() == 0) {
                x = 0;
                System.out.println(
                        " Your Account Balance is 0... \n Please do deposit of required amount for FD Account..");
            } else {
                if (amt > 0) {
                    if (amt <= accounts[index].getBalance()) {
                        x = 0;
                        return amt;
                    } else
                        System.out.println("You have not sufficient balance...!");
                } else
                    System.out.println("Amount must be greater than 0");
            }
        } while (x == 1);
        return -1;
    }

    public void withdrawFDAmountUI(FixedDeposit fd[], int findex, long accountNumber, Account accounts[], int index,
            Scanner keyboard) {

        long days = fd[findex].depositDate.until(LocalDate.now(), ChronoUnit.DAYS);
        System.out.println("No of Days from fixed Deposit created : " + days);
        if (days >= fd[findex].fdDays) {
            System.out.println("Fixed deposit duration is completed");
            fds.getInterestAmt(fd, findex);
            double intAmt = fd[findex].interestAmount;
            System.out.println("Your Interest Amount : " + intAmt + " for FD of " + fd[findex].fdAmount);
            System.out.println("You have successfully withdrawn fixed deposit Amount..");
            fds.withdrawFDAmount(fd, findex, accountNumber, accounts, days, index, intAmt, 0, keyboard);
            getFDBalances(fd, findex, accounts, index);
        } else if (days < fd[findex].fdDays && days > 0) {
            System.out.println("Your FD duration not completed..");
            int ch = 0;
            do {
                System.out.print("if you want please enter 1 to continue or any other number to exit : ");
                try {
                    ch = Integer.parseInt(keyboard.next());
                } catch (NumberFormatException e) {
                    System.out.print("entered input is not a valid integer number \nPlease enter : ");
                }
                if (ch == 1) {
                    int mons = Math.round((days * 12) / 365);
                    float rate = fds.getFDInterestRate(mons);
                    double intAmt = fds.interestCalc(rate, days, fd[index].fdAmount);
                    fd[findex].interestAmount = intAmt;
                    System.out.println("Your Interest Amount : " + intAmt + " for FD of " + fd[findex].fdAmount);
                    System.out.println("You have successfully withdrawn fixed deposit Amount..");
                    fds.withdrawFDAmount(fd, findex, accountNumber, accounts, days, index, intAmt, ch, keyboard);
                    getFDBalances(fd, findex, accounts, index);
                } else if ((ch / 1) == ch && ch != 0) {
                    System.out.println("Thank you ....");
                }
            } while (ch == 0);
        } else {
            int ch = 0;
            System.out.println("You have created Fixed Deposit today only");
            do {
                System.out.print("if you want please enter 1 to continue or any other number to exit : ");
                try {
                    ch = Integer.parseInt(keyboard.next());
                } catch (NumberFormatException e) {
                    System.out.println("entered input is not a valid integer number\nPlease enter : ");
                }
                if (ch == 1) {
                    System.out.println("You have successfully withdrawn fixed deposit Amount..");
                    double intAmt = 0;
                    fds.withdrawFDAmount(fd, findex, accountNumber, accounts, days, index, intAmt, ch, keyboard);
                    getFDBalances(fd, findex, accounts, index);
                } else if ((ch / 1) == ch && ch != 0) {
                    LoginUtilities.print("Thank you...");
                }
            } while (ch == 0);

        }
    }
}
