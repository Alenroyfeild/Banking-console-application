package src.userinterface;

import src.entities.Account;
import src.entities.CIF;
import src.entities.CurrentAccount;
import src.entities.Loan;
import src.entities.SavingsAccount;
import src.services.LoanServices;
import src.utils.LoginUtilities;
import src.utils.Utilties;

public class Notifications {
    // this function is used to display updates of interest for selected accounts
    public static void displayNotificationList(CIF cifs[], int cifindex, Account accounts[], long accountNumber,
            Loan loans[]) {
        Utilties ut = Utilties.getInstance();
        SavingsAccount sa = new SavingsAccount();
        CurrentAccount ca = new CurrentAccount();
        LoanServices.autoPayLoanEMI(accounts, accountNumber, loans);
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

        } else if (accounts[index].accType.equals("CurrentAccount")) {
            ca.displayNotification(accounts, index);
        }
    }

}
