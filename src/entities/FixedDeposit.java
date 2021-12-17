package src.entities;

import java.time.LocalDate;


public class FixedDeposit {
    public static int fDindex = 1;
    public long accountNumber;
    public LocalDate depositDate, noofDays, withdrawDate;
    public double interestAmount, fdAmount;
    public int fdMonths,fdDays;
    public float fdRate;
    public Boolean available = false;

    // parameter constructor to initialise the FixedDeposit Account with custom
    // Account number,deposit date,fixed deposit amount.
    public FixedDeposit(long accountNumber, LocalDate deposiDate, double fdAmt,int fdmons,int fddays,float fdrate, Boolean act) {
        this.accountNumber = accountNumber;
        this.depositDate = deposiDate;
        this.fdAmount = fdAmt;
        this.available = act;
        this.fdMonths=fdmons;
        this.fdRate = fdrate;
        this.fdDays=fddays;
        this.withdrawDate = depositDate;
    }

}
