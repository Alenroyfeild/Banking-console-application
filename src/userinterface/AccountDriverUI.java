package src.userinterface;

import java.util.Scanner;

import src.entities.Account;
import src.entities.CIF;
import src.entities.MiniStatement;
import src.services.AccountDriver;
import src.utils.LoginUtilities;
import src.utils.Utilties;

public class AccountDriverUI {
    Utilties ut = Utilties.getInstance();
    AccountDriver ad = new AccountDriver();

     /**
     * Function to perform Deposit on a selected account
     */
    public void doDepositUI(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber,
            Scanner keyboard) {
        System.out.print("Please enter Deposit Amount : ");
        double amount = LoginUtilities.getAmount(keyboard);
        int status = ad.doDeposit(cifs, cifindex, accounts, count, accountNumber, amount, keyboard);
        if (status == 1) {
            System.out.println("Amount must be less than 5,00,000 rupees...");
        } else if (status == 2) {
            System.out.println("Amount must be less than 50,000 rupees...");
        } else if (status == 3) {
            System.out.println("Transaction completed....");
        }
    }

     /**
     * Function to perform withdraw on a selected account
     */
    public void doWithdrawUI(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber,
            Scanner keyboard) {
        System.out.print("Please enter Withdraw Amount : ");
        double amount = LoginUtilities.getAmount(keyboard);
        int status = ad.doWithdraw(cifs, cifindex, accounts, count, accountNumber, amount, keyboard);
        if (status == 1) {
            System.out.println("Amount must be less than 20,000 rupees...");
        } else if (status == 2) {
            System.out.println("Amount must be less than 5,000 rupees...");
        } else if (status == 3) {
            System.out.println("Transaction completed.....");
        }
    }

       /**
     * Function to perform interest on a selected account
     */
    public void applyInterestUI(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber,
            Scanner keyboard) {
        int status = ad.applyInterest(cifs, cifindex, accounts, count, accountNumber, keyboard);
        if (status == 1) {
            System.out.println("Entered Account number is not a Savings Account");
        }
    }

      // this function is used to transfer amount(NEFT transaction)
    public void doNEFTUI(CIF cifs[], int cifindex, Account accounts[], long accountNumber, Scanner keyboard) {
        System.out.println("Provide the Receivers Account number");
        long accountNumber1 = LoginUtilities.getAccNumber(keyboard);
        System.out.print("Enter the amount to be transfered:");
        double amount = LoginUtilities.getAmount(keyboard);
        ad.doneft(cifs, cifindex, accounts, accountNumber, accountNumber1, amount, keyboard);
    }

    // this function is used to transfer amount(RTGS transaction)
    public void doRTGSUI(CIF cifs[], int cifindex, Account accounts[], long accountNumber, Scanner keyboard) {
        System.out.println("Provide the Receivers Account number");
        long accountNumber1 = LoginUtilities.getAccNumber(keyboard);
        System.out.print("Enter the amount to be transfered:");
        double amount = LoginUtilities.getAmount(keyboard);
        ad.doRTGS(cifs, cifindex, accounts, accountNumber, accountNumber1, amount, keyboard);
    }

    /* function used to transfer the amount to receivers account */
    public void amountTransferUI(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber,
            Scanner keyboard) {
        int x = 0;
        do {
            System.out.println("---------------------------------------------------------------");
            System.out.println("choose the transaction type : ");
            System.out.println("1.NEFT");
            System.out.println("2.RTGS");
            System.out.println("3.Back");
            System.out.println("---------------------------------------------------------------");
            System.out.print("enter choice : ");
            try {
                x = Integer.parseInt(keyboard.next());
            } catch (NumberFormatException e) {
                System.out.println("Entered choice is not a valid integer number\nPlease enter : ");
            }
            if (x == 1) {
                doNEFTUI(cifs, cifindex, accounts, accountNumber, keyboard);
            } else if (x == 2) {
                doRTGSUI(cifs, cifindex, accounts, accountNumber, keyboard);
            } else if (x / 1 == x&&x!=0) {
                System.out.println("back to menu page..");
            }
        } while (x != 3);
        LoginUtilities.screenRefresh(3);
    }

    // this function is used to show last transactions
    public void dominiStatementsUI(Account accounts[], int count, long accountNumber, Scanner keyboard) {
        // Get the account number
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);

        if (index >= 0) {
            if (accounts[index].accType.equals("CurrentAccount")) {
                System.out.println(
                        "\n\n   --    Mini Statement   --\n--------------------------------------------------------------------------");
                System.out.format("%1$-30s%2$-20s%3$-20s%4$-20s\n", "TransactionType", "Date", "Balance", "Fee");
                System.out.println("--------------------------------------------------------------------------");
                for (MiniStatement mini : accounts[index].miniStatements) {
                    System.out.format("%1$-30s%2$-20s%3$-20s%4$-20s\n",
                            mini.transactionType, mini.transactionDate, Math.round(mini.balance), mini.fee);
                }
                System.out.println("--------------------------------------------------------------------------");

            } else {
                System.out.println(
                        "\n\n   --    Mini Statement   --\n--------------------------------------------------------------------------");
                System.out.format("%1$-30s%2$-20s%3$-20s\n", "TransactionType", "Date", "Balance");
                System.out.println("--------------------------------------------------------------------------");
                for (MiniStatement mini : accounts[index].miniStatements) {
                    System.out.format("%1$-30s%2$-20s%3$-20s\n",
                            mini.transactionType, mini.transactionDate, Math.round(mini.balance));
                }
                System.out.println("--------------------------------------------------------------------------");
            }
        }
        LoginUtilities.screenRefresh(10);
    }

}
