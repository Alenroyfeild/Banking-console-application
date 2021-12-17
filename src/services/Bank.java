package src.services;

import java.util.*;

import src.entities.Account;
import src.entities.SavingsAccount;
import src.userinterface.AccountDriverUI;
import src.userinterface.FixedDepositServicesUI;
import src.userinterface.LoanServicesUI;
import src.utils.Utilties;
import src.entities.CIF;
import src.entities.CurrentAccount;
import src.entities.FixedDeposit;
import src.entities.Loan;

import java.time.LocalDate;

public class Bank {
    protected AccountDriverUI ad = new AccountDriverUI();

    // this function is used to create CIF
    public CIF docreateCIF(CIF cifs[], long adharno, String username, int age, long mobileno, long cifno) {
        CIF cifacc = null;
        cifacc = new CIF(cifno, username, mobileno, age, adharno);
        return cifacc;
    }

    // this function is used to check adharno is present in CIF files
    public Boolean checkCIF(CIF cifs[], int cifindex, long adharno1, Scanner keyboard) {
        for (int i = 0; i < cifindex; i++) {
            if (adharno1 == cifs[i].getadharno()) {
                return true;
            }
        }
        return false;
    }

    // this function is used to create Bank Account
    public Account createAccount(CIF cifs[], int cifindex, Account accounts[], String accType, String password,
            long accountNumber, LocalDate accOpenDate, String balType) {
        Account account = null;
        if (accType.equals("CurrentAccount")) {
            account = new CurrentAccount(accountNumber, password, accOpenDate, accType, balType);
        } else {
            account = new SavingsAccount(accountNumber, password, accOpenDate, accType, balType);
        }
        long cifno = cifs[cifindex].getcifno();
        cifs[cifindex].cifList.add(new CIF(cifno, accountNumber, accType, balType));
        return account;
    }

    LoanServices lo = new LoanServices();
    Utilties ut = Utilties.getInstance();

    protected LoanServicesUI lsi = new LoanServicesUI();

    // this function is used to create loan Account
    public Loan createLoanAcc(Loan loans[], Account accounts[], long accountNumber, Scanner keyboard) {
        Loan loa = null;
        int arr[] = lsi.applyLoanAccount(accounts, ut.numAccounts, accountNumber, keyboard);
        double amount = arr[0];
        int noofLoanMonths = arr[1];
        int loanIr = lo.getLoanInterestRate();
        LocalDate date = LocalDate.now();
        Boolean act = true;
        int remainMons = noofLoanMonths;
        loa = new Loan(accountNumber, date, amount, loanIr, noofLoanMonths, remainMons, act);
        return loa;

    }

    FixedDepositServicesUI fdsi = new FixedDepositServicesUI();
    FixedDepositServices fds = new FixedDepositServices();

    // this function is used to withdraw Amount from the custom Fixed Deposit
    // Account
    public void dowithdrawFDAmt(FixedDeposit fd[], Account accounts[], long accountNumber, int index, int findex,
            Scanner keyboard) {

        fdsi.withdrawFDAmountUI(fd, findex, accountNumber, accounts, index, keyboard);
    }

    // this function is used to create Fixed Deposit Account
    public FixedDeposit createFD(CIF cifs[], int cifindex, FixedDeposit fd[], Account accounts[], long accountNumber,
            int index,
            Double amount, Scanner keyboard) {
        FixedDeposit fda = null;
        int a[] = fdsi.getMonths(keyboard);
        int fdmons = a[0];
        int fddays = a[1];
        float fdrate = fds.getFDInterestRate(a[0]);
        LocalDate date = LocalDate.now();
        Boolean act = true;
        fda = new FixedDeposit(accountNumber, date, amount, fdmons, fddays, fdrate, act);
        cifs[cifindex].cifList.add(new CIF(cifs[cifindex].getcifno(), accountNumber, "FixedDeposit",""));
        return fda;
    }

    // this function is used to apply for Fixed Deposit Account
    public void applyFDAcc(CIF cifs[], int cifindex, Account accounts[], FixedDeposit fd[], long accountNumber,
            long accountNumber1, int index, int findex,double amount,
            Scanner keyboard) {
        if (findex > -1) {
            if (fd[findex].available == false) {
                fdsi.applyFDAccountUI(fd, accounts, accountNumber, index, findex,amount, keyboard);
            }
        }
        if (accountNumber1 != -1) {
            fdsi.applyFDAccountUI(fd, accounts, accountNumber, index, findex,amount, keyboard);
        }
    }

    // function is used to search adhar number in the CIF
    public int searchCIFadharno(CIF cifs[], int cifindex, long adharno) {
        for (int i = 0; i < cifindex; i++) {
            if (cifs[i].getadharno() == adharno) {
                return i;
            }
        }
        return -1;
    }

    // this function is used to search account number in the CIF
    public boolean searchCIFAccno(CIF cifs[], int cifindex, long accountNumber) {
        for (CIF c : cifs[cifindex].cifList) {
            if (c.accountNumber == accountNumber) {
                return true;
            }
        }
        return false;
    }

}
