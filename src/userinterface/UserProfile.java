package src.userinterface;

import java.util.*;

import src.entities.Account;
import src.entities.CIF;
import src.entities.FixedDeposit;
import src.entities.Loan;
import src.services.FixedDepositServices;
import src.services.LoanServices;
import src.utils.LoginUtilities;

public class UserProfile  {
    // this functoin is used to display Account details
    public void userAccBook(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber) {
        int index = LoginUtilities.searchAccount(accounts, count, accountNumber);
        System.out.println("User Name          : " + cifs[cifindex].getUsername());
        System.out.println("Account number     : " + accountNumber);
        System.out.println("Account Type       : " + accounts[index].accType);
        System.out.println("Balance Type       : " + accounts[index].balanceType);
        System.out.println("Age                : " + cifs[cifindex].getAge());
        System.out.println("Mobile number      : " + cifs[cifindex].getMobileno());
        System.out.println("Account opened Date: " + accounts[index].getdate());
    }

    LoanServices lo = new LoanServices();
    FixedDepositServices f = new FixedDepositServices();

    // this function is used to update mobileno ,password and to display details of
    // loan,Fixed deposit Account details
    public void userAccount(CIF cifs[], int cifindex, Account accounts[], Loan loans[], FixedDeposit fd[], int count,
            long accountNumber,
            Scanner keyboard) {
        int index = LoginUtilities.searchAccount(accounts, count, accountNumber);
        if (LoginUtilities.login(accounts, count, accountNumber, keyboard)) {
            int x=0;
            do {
                System.out.println("---------------------------------------------------");
                System.out.println("\n   ---    User Account Profile Page  ---\n");
                System.out.println("---------------------------------------------------");
                System.out.println("\n1.Passbook");
                System.out.println("2.Profile Update");
                System.out.println("3.Loan Details");
                System.out.println("4.Fixed Deposit Details");
                System.out.println("5.Display All Accounts details");
                System.out.println("6.Back");
                try{
                    System.out.print("Enter choice : ");
                    x=Integer.parseInt(keyboard.next());
                 } 
                 catch (NumberFormatException e) 
                 {
                     System.out.println("entered choice is not a valid integer number");
                 }
                if (x == 1) {
                    userAccBook(cifs, cifindex, accounts, count, accountNumber);
                } else if (x == 2) {
                    int y=0;
                    do {
                        System.out.println("---------------------------------------------");
                        System.out.println("    ----   Profile Update page   ----");
                        System.out.println("1.Mobile Number");
                        System.out.println("2.Password");
                        System.out.println("Quit");
                        try{
                            y=Integer.parseInt(keyboard.next());
                         } 
                         catch (NumberFormatException e) 
                         {
                             System.out.println(y + " is not a valid integer number");
                         }
                        if (y == 1) {
                            System.out.println(" Update Mobile Number");
                            long mobile = LoginUtilities.getMobileNo(keyboard);
                            cifs[cifindex].setMobileno(mobile);
                            System.out.println("Successfully Mobile number updated...");
                            y = 3;
                        } else if (y == 2) {
                            System.out.println("Enter New Password");
                            String pass = keyboard.next();
                            accounts[index].setPassword(pass);
                            System.out.println("Successfully Password updated...");
                            y = 3;
                        }
                    } while (y == 1 || y == 2);
                } else if (x == 3) {
                    int lindex = lo.searchloanAcc(loans, Loan.loanindex, accountNumber);
                    if (lindex >= 0) {
                        System.out.println("--------------------------------------------------\n");
                        System.out.println("     ---   Loan Details   ---\n");
                        System.out.println("Account Number   : " + accountNumber);
                        System.out.println("User Name        : " + cifs[cifindex].getUsername());
                        System.out.println("Loan Amount      : " + loans[lindex].loanAmount);
                        System.out.println("Interest Rate    : " + loans[lindex].interestAmount);
                        System.out.println("EMI s remaining  : "+loans[lindex].monthsRemain);
                        System.out.println("Loan Applied Date: " + loans[lindex].loanDate);
                    } else {
                        System.out.println("You need to take loan...");
                    }
                } else if (x == 4) {
                    int findex = f.searchFDAcc(fd, FixedDeposit.fDindex, accountNumber);
                    if (findex >= 0) {
                        System.out.println("------------------------------------------------\n");
                        System.out.println("      ---    Fixed Deposit Details    ---\n");
                        System.out.println("Account Number      : " + accountNumber);
                        System.out.println("User Name           : " + cifs[cifindex].getUsername());
                        System.out.println("Fixed Deposit Amount: " + fd[findex].fdAmount);
                        System.out.println("Fixed Deposit rate  : "+ fd[findex].fdRate);
                        System.out.println("Fixed Deposit Date  : " + fd[findex].depositDate);
                    }else{
                        System.out.println("You need to create FD Account....");
                    }
                } else if (x == 5) {
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.println("       --  All Accounts of You  --\n");
                    System.out.println(" -> "+cifs[cifindex].getUsername());
                    System.out.println(" CIF No : " + cifs[cifindex].getcifno());
                    System.out.println("--------------------------------------------------------------------------");
                    System.out.format("    %1$-30s%2$-20s\n", "AccountNumber", "AccountType");
                    System.out.println("--------------------------------------------------------------------------");
                    for (CIF ciflists : cifs[cifindex].cifList) {
                        System.out.format("    %1$-30s%2$-20s\n",
                                ciflists.accountNumber, ciflists.accountType);
                    }
                    System.out.println("--------------------------------------------------------------------------");
                } else {
                    System.out.println("Back to Bank Menu Page");
                }
            } while (x != 6);
        }else{
            System.out.println("you have entered wrong password..");
        }
    }
}
