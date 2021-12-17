package src.entities;

import java.time.LocalDate;

public class Loan {
    public static int loanindex = 2;
    public long accountNumber;
    public LocalDate paidDate, loanDate, noofDays;
    public double interestAmount, loanAmount;
    public Boolean available = false;
    public int monthsRemain = 0, daysCount;
    public final int DAYS;

    public int noofLoanMonths;

    // parameter constructor to initialise the loan with custom account number,loan
    // amount,interest amount.
    public Loan(long accountNumber, LocalDate loanDate, double loanAmt, double interestAmt, int noofLoanMonths,
            int monthsRemain,
            Boolean act) {
        this.accountNumber = accountNumber;
        this.loanDate = loanDate;
        this.paidDate = loanDate;
        this.loanAmount = loanAmt;
        this.interestAmount = interestAmt;
        this.noofLoanMonths = noofLoanMonths;
        this.monthsRemain = monthsRemain;
        this.available = act;
        this.DAYS = 30;
        this.daysCount = 0;
    }

}