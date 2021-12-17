package src.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import src.entities.*;

public class FixedDepositServices {

    // FixedDepositServicesUI fdsi = new FixedDepositServicesUI();

    // this function is used to get interest amount from fixed deposit loan Amount
    // and no.of days between last withdraw to today
    public void getInterestAmt(FixedDeposit fd[], int findex) {
        int mons = fd[findex].fdMonths;
        int days = (mons * 365) / 12;
        double fdAmt = fd[findex].fdAmount;
        float rate = getFDInterestRate(mons);
        double intAmt = interestCalc(rate, days, fdAmt);
        fd[findex].interestAmount = intAmt;
    }

    // this function is used to return interest rate based on amount
    public float getFDInterestRate(int mons) {
        if (mons < 6 && mons >= 1) {
            float ir = 6.5f;
            return ir;
        } else if (mons >= 6 && mons < 12) {
            float ir = 7.5f;
            return ir;
        } else if (mons >= 12 && mons < 24) {
            float ir = 8.25f;
            return ir;
        } else if (mons >= 24) {
            float ir = 8.5f;
            return ir;
        }
        return 0;
    }

    // this function is used to count days from last withdraw day to present day
    public long getFDDays(FixedDeposit fd[], int index) {
        if (!fd[index].depositDate.equals(LocalDate.now())) {
            long days = fd[index].withdrawDate.until(LocalDate.now(), ChronoUnit.DAYS);
            return days;
        }
        return 0;
    }

    // this function is used to calculate the interest
    public double interestCalc(float rate, long days, double lamount) {
        double amt = (rate * days * lamount) / 36500;
        return amt;
    }

    // this function is used to check balance is available in Fixed deposit Account
    // public double checkFDAmount(FixedDeposit fd[], int findex, Scanner keyboard)
    // {
    // int x = 0;
    // do {
    // double amt = LoginUtilities.getAmount(keyboard);
    // if (amt >= 0) {
    // if (amt <= (fd[findex].fdAmount + fd[findex].interestAmount)) {
    // x = 0;
    // return amt;
    // } else
    // LoginUtilities.print("You have not sufficient Amount....");
    // } else
    // LoginUtilities.print("Amount must be greater than 0...");
    // x = 1;
    // } while (x == 1);
    // return 0;
    // }

    // this function is used to search the Account number in the accounts
    public int searchFDAcc(FixedDeposit fd[], int count, long accountNumber) {
        for (int i = 0; i < count; i++)
            if (fd[i].accountNumber == accountNumber)
                return i;
        return -1;
    }

    // this function is used to create Fixed deposit Account
    public void applyFDAccount(FixedDeposit fd[], Account accounts[], long accountNumber, int index, int findex,
            Double amount,
            Scanner keyboard) {
        // double amount = fd[findex].fdAmount;
        accounts[index].miniStatements
                .add(new MiniStatement("FDAmountDeposit", LocalDate.now(), -amount));
        double balance = accounts[index].getBalance();
        balance -= amount;
        accounts[index].setBalance(balance);
        fd[findex].fdAmount = amount;
    }

    // this function is used to withdraw amount from Fixed deposit Account
    public void withdrawFDAmount(FixedDeposit fd[], int findex, long accountNumber, Account accounts[], long days,
            int index, Double intAmt, int ch,
            Scanner keyboard) {

        if (days >= fd[findex].fdDays) {
            double bal = accounts[index].getBalance();
            bal += fd[findex].fdAmount + fd[findex].interestAmount;
            accounts[index].miniStatements.add(new MiniStatement("FDAmountWithdraw", LocalDate.now(), +fd[findex].fdAmount));
            accounts[index].miniStatements
                    .add(new MiniStatement("FDInterest", LocalDate.now(), +fd[findex].interestAmount));
            fd[findex].fdAmount = 0;
            fd[findex].interestAmount = 0;
            accounts[index].setBalance(bal);
            fd[findex].available = false;
        } else if (days < fd[findex].fdDays && days > 0) {
            if (ch == 1) {
                double bal = accounts[index].getBalance();
                bal += fd[findex].fdAmount + fd[findex].interestAmount;
                accounts[index].miniStatements
                        .add(new MiniStatement("FDAmountWithdraw", LocalDate.now(), +fd[findex].fdAmount));
                accounts[index].miniStatements
                        .add(new MiniStatement("FDInterest", LocalDate.now(), +fd[findex].interestAmount));
                accounts[index].setBalance(bal);
                fd[findex].available = false;
                fd[findex].fdAmount = 0;
                fd[findex].interestAmount = 0;
                // getFDBalances(fd, findex, accounts, index);
            }

        } else {
            if (ch == 1) {
                double bal = accounts[index].getBalance();
                bal += fd[findex].fdAmount + fd[findex].interestAmount;
                accounts[index].miniStatements
                        .add(new MiniStatement("FDAmountWithdraw", LocalDate.now(), +fd[findex].fdAmount));
                accounts[index].miniStatements
                        .add(new MiniStatement("FDInterest", LocalDate.now(), +fd[findex].interestAmount));
                accounts[index].setBalance(bal);
                fd[findex].available = false;
                fd[findex].fdAmount = 0;
                fd[findex].interestAmount = 0;
                // getFDBalances(fd, findex, accounts, index);
            }

        }
    }
}
