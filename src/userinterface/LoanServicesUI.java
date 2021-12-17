package src.userinterface;

import java.util.Scanner;

import src.entities.Account;
import src.entities.Loan;
import src.services.LoanServices;
import src.utils.LoginUtilities;

public class LoanServicesUI {
    LoanServices ls = new LoanServices();

    // this function is used to display interest details based on Amount
    public void loanAmountDetails() {
        System.out.println("\nLoan interest is 12% on amount ");
        System.out.println("you can pay EMI or ur wish amount");
        System.out.println("loan amount is based on Accounttype and Balancetype");
        System.out.println("SavingsAccount   - ZeroBalance    - below 10 k");
        System.out.println("SavingsAccount   - MinimumBalance - below 2 lakh");
        System.out.println("CurrentAccount   - ZeroBalance    - below 1 lakh");
        System.out.println("CurrentAccount   - MinimumBalance - below 20 lakh");

    }

    // this function is used to display loan balances
    public void displayLoanBalances(Account accounts[], Loan loans[], int lcount, int count, int rate,
            Scanner keyboard) {
        System.out.format("Loan Amount to pay      : %.2f\n", loans[lcount].loanAmount);
        System.out.println("Interest rate          : " + rate);
        System.out.format("Interest amount to pay  : %.2f\n", Math.round(loans[lcount].interestAmount));
        System.out.format("Account balance         : %.2f\n", Math.round(accounts[count].getBalance()));
    }

    public int[] applyLoanAccount(Account accounts[], int count, long accountNumber, Scanner keyboard) {
        int index = LoginUtilities.searchAccount(accounts, count, accountNumber);
        double amount;
        int noofMonths;
        int a[] = new int[2];
        System.out.println("You are Eligible for loan :):) ");
        loanAmountDetails();
        amount = getLoanAmount(accounts, index, keyboard);
        noofMonths = getnoofMonths(accounts, index, keyboard);
        System.out.println("initial account balance  : " + accounts[index].getBalance());
        System.out.println("Loan amount ...");
        ls.applyLoanAccount(accounts, count, accountNumber, index, amount);
        System.out.println("Loan Amount is added to account  : " + accounts[index].getBalance());
        a[0] = (int) amount;
        a[1] = noofMonths;
        return a;
    }

    // this function is used to get loan Amount
    public double getLoanAmount(Account accounts[], int index, Scanner keyboard) {
        double amt;
        int ref = 0;
        do {
            System.out.print("Enter amount for loan      : ");
            amt = LoginUtilities.getAmount(keyboard);
            if (accounts[index].accType.equals("SavingsAccount")) {
                if (accounts[index].balanceType.equals("ZeroBalanceAccount")) {
                    if (amt <= 10000) {
                        ref = 1;
                        return amt;
                    } else {
                        System.out.println("loan amount is below or equal to 10k .....");
                    }
                } else {
                    if (amt <= 200000) {
                        ref = 1;
                        return amt;
                    } else {
                        System.out.println("loan amount is below or equal to 2 lakhs....");
                    }
                }
            } else {
                if (accounts[index].accType.equals("ZeroBalanceAccount")) {
                    if (amt <= 100000) {
                        ref = 1;
                        return amt;
                    } else {
                        System.out.println("loan amount is below or equal to 1 lakh......");
                    }
                } else {
                    if (amt <= 2000000) {
                        ref = 1;
                        return amt;
                    } else {
                        System.out.println("loan amount is below 20lakhs.....");
                    }
                }
            }
        } while (ref == 0);
        return -1;
    }

    // this function is to used to get duration of months for loan Account
    public int getnoofMonths(Account accounts[], int index, Scanner keyboard) {
        int ref = 0;
        do {
            if (accounts[index].accType.equals("CurrentAccount")) {
                if (accounts[index].accType.equals("MinimumBalanceAccount")) {
                    System.out.println("enter the no of months to pay loan");
                    System.out.println("list : 12 24 36 48 60 72 84 96 108 120 months");
                    int m = keyboard.nextInt();
                    if (m % 12 == 0) {
                        ref = 1;
                        return m;
                    } else {
                        System.out.println("no of months must be any of the given list..");
                    }
                } else {
                    System.out.println("enter the no of months to pay loan");
                    System.out.println("liast : 12 24 36months");
                    int m = keyboard.nextInt();
                    if (m % 12 == 0 & m <= 36) {
                        ref = 1;
                        return m;
                    } else {
                        System.out.println("no of months must be any of the given list..");
                    }
                }
            } else {
                if (accounts[index].accType.equals("MinimumBalanceAccount")) {
                    System.out.println("enter the no of months to pay loan");
                    System.out.println("12 24 36 48 months");
                    int m = keyboard.nextInt();
                    if (m % 12 == 0 && m <= 48) {
                        ref = 1;
                        return m;
                    } else {
                        System.out.println("no of months must be any of the given list..");
                    }
                } else {
                    System.out.println("enter the no of months to pay loan");
                    System.out.println("12 24 months");
                    int m = keyboard.nextInt();
                    if (m % 12 == 0 && m <= 24) {
                        ref = 1;
                        return m;
                    } else {
                        System.out.println("no of months must be any of the given list..");
                    }
                }
            }
        } while (ref == 0);
        return -1;
    }

    public void payLoanAmount(Loan loans[], Account accounts[], long accountNumber, int lindex, int index,
            double lamount,
            Scanner keyboard) {
        int ref = 0;
        do {
            System.out.println("choose type of paying : ");
            System.out.println("1.EMI");
            System.out.println("2.Total pay");
            System.out.println("3.Back");
            int ch = 0;
            do {
                System.out.print("enter choice : ");
                try {
                    ch = Integer.parseInt(keyboard.next());
                } catch (NumberFormatException e) {
                    System.out.println("entered choice is not a valid integer number\nplease enter choice : ");
                }
                if (ch == 1) {
                    if (loans[lindex].available) {
                        int rate = ls.getLoanInterestRate();
                        double intAmt = ls.getLoanEMIAmount(loans, lindex, lamount, rate);
                        System.out.println("EMI amount : " + Math.round(intAmt));
                        System.out.print("enter 1 to pay or any integer to exit : ");
                        int q = 0;
                        do {
                            try {
                                q = Integer.parseInt(keyboard.next());
                            } catch (NumberFormatException e) {
                                System.out.print("entered input is not a valid integer number\nenter 1 to pay or any integer to exit : ");
                            }
                            if (q == 1) {
                                if (loans[lindex].monthsRemain >= 1) {
                                    if (intAmt <= accounts[index].getBalance()) {

                                        ls.payLoanAmount(loans, accounts, accountNumber, lindex, index, lamount, intAmt,
                                                ch, q, keyboard);
                                        ref = 1;
                                        LoginUtilities.print("Successfully EMI paid ....");
                                        LoginUtilities.print("Remaining months to pay :" + loans[lindex].monthsRemain);

                                    } else {
                                        LoginUtilities.print("You have not sufficient balance...");
                                        ref = 1;
                                    }

                                } else {
                                    LoginUtilities.print("Successfully loan Amount paid......");
                                    ref = 1;
                                }
                            } else if((q/1==q)&&q!=0) {
                                System.out.println("You selected not to pay EMI \nThank you...");
                                ref = 1;
                            }
                        } while (q == 0);
                    } else {
                        System.out.println("You have already loan amount is paid ....");
                    }
                } else if (ch == 2) {
                    System.out.println("Account Balance available : " + Math.round(accounts[index].getBalance()));
                    double arr[] = ls.getLoanBalance(loans, lindex, lamount);
                    double loanBal = arr[0];
                    double rintamt = arr[1];
                    System.out.println("Remaining loan amount to pay : " + loanBal);
                    if (loanBal <= accounts[index].getBalance()) {
                        double intAmt = 0;
                        ls.payLoanAmount(loans, accounts, accountNumber, lindex, index, lamount, intAmt, ch, 0,
                                keyboard);
                        double bal = accounts[index].getBalance();
                        bal -= loanBal;
                        accounts[index].setBalance(bal);
                        System.out.println("Successfully loan Amount paid......");
                        System.out.println("You saved upto : " + rintamt);
                        System.out.println("Remaining Balance in Account : " + accounts[index].getBalance());
                        ref = 1;
                    } else {
                        LoginUtilities.print("You have not sufficient balance..!");
                        ref = 1;
                    }
                } else if (ch / 1 == ch&&ch!=0) {
                    System.out.println("Back to menu page..");
                    ref = 1;
                }

            } while (ch == 0);

        } while (ref == 0);
    }
}
