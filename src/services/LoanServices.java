package src.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import src.entities.*;
import src.utils.LoginUtilities;
import src.utils.Utilties;

public class LoanServices {
    // this function is used to search the loan Account
    public int searchloanAcc(Loan loans[], int count, long accountNumber) {
        for (int i = 0; i < count; i++)
            if (loans[i].accountNumber == accountNumber)
                return i;
        return -1;
    }

    //this function is used to autoPay EMI from user Account
    public static void autoPayLoanEMI(Account accounts[], long accountNumber, Loan loans[]) {
        Utilties ut = Utilties.getInstance();
        LoanServices lo = new LoanServices();
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        int lindex = lo.searchloanAcc(loans, Loan.loanindex, accountNumber);
        if (lindex >= 0) {
            double lamount = loans[lindex].loanAmount;
            lo.updateEMI(loans, accounts, accountNumber, lindex, index, lamount);
        }
    }
    // this function is used to pay the loan EMI Amount automatically

    public void updateEMI(Loan loans[], Account accounts[], long accountNumber, int lindex, int index, double lamount) {
        long days = getnoofDays(loans, lindex);
        loans[lindex].daysCount = (int) days;
        int x = 0;
        while (loans[lindex].daysCount >= loans[lindex].DAYS) {
            if (loans[lindex].monthsRemain >= 1) {
                int rate = getLoanInterestRate();
                double intAmt = getLoanEMIAmount(loans, lindex, lamount, rate);
                double ba = accounts[index].getBalance();
                ba -= intAmt;
                accounts[index].setBalance(ba);
                loans[lindex].monthsRemain -= 1;
                // System.out.println("Successfully EMI paid ....");
                // System.out.println("Remaining months to pay :" + loans[lindex].monthsRemain);
                accounts[index].miniStatements
                        .add(new MiniStatement("EMI Amount Paid", LocalDate.now(), -intAmt));
                if (loans[lindex].monthsRemain == 0) {
                    loans[lindex].available = false;
                    loans[lindex].loanDate = LocalDate.now();
                }
                loans[lindex].daysCount -= 30;
                x = 1;
            }
        }
        if (x == 1) {
            loans[index].paidDate = LocalDate.now();
        }
    }

    // this function is used to pay the loan Amount
    public void payLoanAmount(Loan loans[], Account accounts[], long accountNumber, int lindex, int index,
            double lamount, double intAmt, int ch, int q,
            Scanner keyboard) {
        if (ch == 1) {
            if (q == 1) {
                if (loans[lindex].monthsRemain >= 1) {
                    if (intAmt <= accounts[index].getBalance()) {
                        double ba = accounts[index].getBalance();
                        ba -= intAmt;
                        accounts[index].setBalance(ba);
                        loans[lindex].monthsRemain -= 1;
                        long days = loans[lindex].loanDate.until(LocalDate.now(), ChronoUnit.DAYS);
                        int x = ((int) days % 30);
                        loans[index].paidDate = LocalDate.now().plusDays(x);
                        accounts[index].miniStatements
                                .add(new MiniStatement("EMI Amount Paid", LocalDate.now(), -intAmt));

                        if (loans[lindex].monthsRemain == 0) {
                            loans[lindex].available = false;
                            loans[lindex].loanDate = LocalDate.now();
                        }
                    }
                }
            }
        }
        if (ch == 2) {
            double arr[]=getLoanBalance(loans, lindex, lamount);
            double loanBal=arr[0];
            if (loanBal <= accounts[index].getBalance()) {
                loans[lindex].monthsRemain = 0;
                accounts[index].miniStatements
                        .add(new MiniStatement("EMI Amount Paid", LocalDate.now(), -loanBal));
                
                loans[lindex].available = false;
            } 
        }
    }

    //this function is used to get loan Amount and remaining interest amount
    public double[] getLoanBalance(Loan loans[],int lindex,double lamount){
        double arr[]=new double[2];
        int remainMons = loans[lindex].monthsRemain;
        double tintamt = (12 * lamount * loans[lindex].noofLoanMonths) / (1200);
        double rintamt = (tintamt / loans[lindex].noofLoanMonths) * remainMons;
        double loanBal = ((lamount * remainMons) / (loans[lindex].noofLoanMonths));
        arr[0]=loanBal;
        arr[1]=rintamt;
        return arr;
    }
    // this function is used to get loan interest amount to pay
    public double getLoanEMIAmount(Loan loans[], int lindex, double lamount, int rate) {
        int mons = loans[lindex].noofLoanMonths;
        double intAmt = interestCalc(rate, mons, lamount);
        return intAmt;
    }

    // this function is used to calculate the interest
    public double interestCalc(int rate, int noofmons, double lamount) {
        double amt, intamt = (rate * lamount * noofmons) / (1200);
        amt = (lamount + intamt) / noofmons;
        return amt;
    }

    // this function is used to apply loan Account
    public void applyLoanAccount(Account accounts[], int count, long accountNumber,int index,double amount) {
        accounts[index].miniStatements.add(new MiniStatement("Loan Amount Credited", LocalDate.now(), +amount));
        amount += accounts[index].getBalance();
        accounts[index].setBalance(amount);
    }

 
    // this function is used to count days for interest calculation
    public long getnoofDays(Loan[] loans, int index) {
        long days = loans[index].paidDate.until(LocalDate.now(), ChronoUnit.DAYS);
        return days;
    }

    // this function is used to check eligibility for loan Acocunt
    public Boolean checkEligibleLoan(long days) {
        return true;
    }

    // this function is used to return interest rate based on amount
    public int getLoanInterestRate() {
        int ir = 12;
        return ir;
    }
}
