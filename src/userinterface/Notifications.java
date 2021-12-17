package src.userinterface;

import java.time.LocalDate;

import src.entities.*;
import src.services.LoanServices;
import src.utils.LoginUtilities;
import src.utils.Utilties;

public class Notifications {
    //this function is used to display updates of interest for selected accounts
    public static void displayNotificationList(CIF cifs[], int cifindex, Account accounts[], long accountNumber) {
        Utilties ut = Utilties.getInstance();
        SavingsAccount sa = new SavingsAccount();
        CurrentAccount ca = new CurrentAccount();
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        if (accounts[index].lastWithdrawdate == null) {
            accounts[index].lastWithdrawdate = accounts[index].accOpenDate;
        }

        System.out.println("---------------------------------------------------------------");
        System.out.println("  -- Notification page  -- ");
        System.out.println("Hi " + cifs[cifindex].getUsername());
        System.out.println("---------------------------------------------------------------");
        if (accounts[index].accType.equals("SavingsAccount")) {
            sa.displayNotifications(accounts, index);
            double bal = sa.calcInterest(cifs, cifindex, accounts, index, accounts[index].lastWithdrawdate);
            if (bal != 0) {
                accounts[index].miniStatements
                        .add(new MiniStatement("Savings interest", LocalDate.now(), +bal));
                bal += accounts[index].getBalance();
                accounts[index].setBalance(bal);
                accounts[index].setLastWithdrawDate();
            }
        } else if (accounts[index].accType.equals("CurrentAccount")) {
            ca.displayNotification(accounts, index);
        }

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
}
