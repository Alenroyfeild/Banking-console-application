package src.services;

import java.time.LocalDate;
import java.util.*;

import src.entities.*;
import src.utils.LoginUtilities;
import src.utils.Utilties;

public class AccountDriver {

    SavingsAccount sa = new SavingsAccount();
    Utilties ut = Utilties.getInstance();

    /**
     * Function to perform Deposit on a selected account
     */
    public int doDeposit(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber,
            double amount, Scanner keyboard) {
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);
        if (index >= 0) {
            // Amount
            int ref = 0;
            LocalDate lwd = accounts[index].lastWithdrawdate;
            do {
                if (accounts[index].accType.equals("SavingsAccount")) {
                    if (accounts[index].balanceType.equals("MinimumBalanceAccount")) {
                        if (amount < 500000) {
                            accounts[index].deposit(cifs, cifindex, amount, lwd);
                            accounts[index].miniStatements.add(new MiniStatement("Deposit", LocalDate.now(), +amount));
                            ref = 1;
                        } else {
                            return 1;
                        }
                    } else {
                        if (amount < 50000) {
                            accounts[index].deposit(cifs, cifindex, amount, lwd);
                            accounts[index].miniStatements.add(new MiniStatement("Deposit", LocalDate.now(), +amount));
                            ref = 1;
                        } else {
                            return 2;
                        }
                    }
                } else {
                    accounts[index].deposit(cifs, cifindex, amount, lwd);
                    accounts[index].miniStatements.add(new MiniStatement("Deposit", LocalDate.now(), +amount));
                    ref = 1;
                    return 3;
                }
            } while (ref == 0);
        }
        LoginUtilities.screenRefresh(5);
        return 0;
    }

    CurrentAccount a1 = new CurrentAccount();

    /**
     * Function to perform withdraw on a selected account
     */
    public int doWithdraw(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber, double amount,
            Scanner keyboard) {
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);
        if (index >= 0) {
            // Amount
            int ref = 0;
            LocalDate lastwithdrawDate = accounts[index].lastWithdrawdate;
            do {
                if (accounts[index].accType.equals("SavingsAccount")) {
                    if (accounts[index].balanceType.equals("MinimumBalanceAccount")) {
                        if (amount < 20000) {
                            accounts[index].withdraw(cifs, cifindex, amount, lastwithdrawDate);
                            accounts[index].miniStatements.add(new MiniStatement("Withdraw", LocalDate.now(), -amount));
                            ref = 1;
                        } else {
                            return 1;
                        }
                    } else {
                        if (amount < 5000) {
                            accounts[index].withdraw(cifs, cifindex, amount, lastwithdrawDate);
                            accounts[index].miniStatements.add(new MiniStatement("Withdraw", LocalDate.now(), -amount));
                            ref = 1;
                        } else {
                            return 2;
                        }
                    }
                } else {
                    accounts[index].withdraw(cifs, cifindex, amount, lastwithdrawDate);
                    accounts[index].miniStatements
                            .add(new MiniStatement("Withdraw", LocalDate.now(), -amount, -a1.getFees()));
                    ref = 1;
                    return 3;
                }
            } while (ref == 0);
        }
        LoginUtilities.screenRefresh(5);
        return 0;
    }

    /**
     * Function to perform check balance on a selected account
     */
    public void doBalanceEnquiry(Account accounts[], int count, long accountNumber, Scanner keyboard) {
        // Get the account number
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);

        if (index >= 0) {
            // Amount
            accounts[index].balanceEnquiry();

        }
        LoginUtilities.screenRefresh(5);
    }

    /**
     * Function to perform interest on a selected account
     */
    public int applyInterest(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber,
            Scanner keyboard) {
        // Get the account number
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);

        if (index >= 0) {
            // must be instance of savings account
            if (accounts[index] instanceof SavingsAccount) {
                ((SavingsAccount) accounts[index]).applyInterest(cifs, cifindex);
            } else {
                return 1;
            }
        }
        LoginUtilities.screenRefresh(5);
        return 0;
    }

    // this function is to used return NEFT fee based on amount
    double getNEFTFee(double amount) {
        double fee;
        if (amount < 10000) {
            fee = 2.5;
        } else if (amount >= 10000 && amount < 10000) {
            fee = 5;
        } else if (amount >= 100000 && amount < 200000) {
            fee = 15;
        } else {
            fee = 25;
        }
        return fee;
    }

    // this function is used to return RTGS fee based on amount
    double getRTGSFee(double amount) {
        double fee;
        if (amount > 200000 && amount < 500000) {
            fee = 25;
        } else {
            fee = 50;
        }
        return fee;
    }

    Account ao = new SavingsAccount();

    // this function is used to transfer amount(NEFT transaction)
    public void doneft(CIF cifs[], int cifindex, Account accounts[], long accountNumber, long accountNumber1,
            double amount, Scanner keyboard) {
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);
        if (index >= 0) {
            int index1 = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber1);
            if (index1 >= 0) {
                LocalDate date = accounts[index].getdate();
                int ref = 0;
                do {
                    if (accounts[index].accType.equals("CurrentAccount")) {
                        double fee = getNEFTFee(amount);
                        if (amount + fee <= accounts[index].getBalance()) {
                            LoginUtilities.print("Sender info:");
                            accounts[index].withdraw(cifs, cifindex, amount, date);
                            accounts[index].miniStatements
                                    .add(new MiniStatement("NEFTwithdraw", ao.getdate(), -amount, -fee));
                            LoginUtilities.print("Receiver info:");
                            accounts[index1].deposit(cifs, cifindex, amount, date);
                            accounts[index1].miniStatements
                                    .add(new MiniStatement("NEFTdeposit", LocalDate.now(), +amount));
                            LoginUtilities.print("Amount added to receivers Account Successfully");
                            ref = 1;
                        } else {
                            LoginUtilities.print(" Insufficient balance:");
                        }
                    } else {
                        if (amount < 200000) {
                            double fee = getNEFTFee(amount);
                            if (amount + fee <= accounts[index].getBalance()) {
                                LoginUtilities.print("Sender info:");
                                accounts[index].withdraw(cifs, cifindex, amount, date);
                                accounts[index].miniStatements
                                        .add(new MiniStatement("NEFTwithdraw", LocalDate.now(), -amount, -fee));
                                LoginUtilities.print("Receiver info:");
                                accounts[index1].deposit(cifs, cifindex, amount, date);
                                accounts[index1].miniStatements
                                        .add(new MiniStatement("NEFTdeposit", LocalDate.now(), +amount));
                                LoginUtilities.print("Amount added to receivers Account Successfully");
                                ref = 1;
                            } else {
                                LoginUtilities.print(" Insufficient balance:");
                                LoginUtilities.print("If you want to go back enter 1 : ");
                                int z = keyboard.nextInt();
                                if (z == 1) {
                                    ref = 1;
                                }
                            }
                        } else {
                            LoginUtilities.print("Transaction Amount is less than 2 lakhs....");
                        }
                    }

                } while (ref == 0);
            } else {
                LoginUtilities.print("No account exist with Receivers AccountNumber: " + accountNumber1);
            }
        }

        LoginUtilities.screenRefresh(10);
    }

    // this function is used to transfer amount(RTGS transaction)
   public void doRTGS(CIF cifs[], int cifindex, Account accounts[], long accountNumber, long accountNumber1, double amount,
            Scanner keyboard) {
        int index = LoginUtilities.checkAccNumber(accounts, ut.numAccounts, accountNumber, keyboard);
        if (index >= 0) {
            int index1 = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber1);
            if (index1 >= 0) {
                LocalDate date = accounts[index].getdate();
                int ref = 0;
                do {
                    if (accounts[index].accType.equals("CurrentAccount")) {
                        if (amount >= 200000) {
                            double fee = getRTGSFee(amount);
                            if (amount + fee <= accounts[index].getBalance()) {
                                LoginUtilities.print("Sender info:");
                                accounts[index].withdraw(cifs, cifindex, amount, date);
                                accounts[index].miniStatements
                                        .add(new MiniStatement("NEFTwithdraw", LocalDate.now(), -amount, -fee));
                                LoginUtilities.print("Receiver info:");
                                accounts[index1].deposit(cifs, cifindex, amount, date);
                                accounts[index1].miniStatements
                                        .add(new MiniStatement("NEFTdeposit", LocalDate.now(), +amount));
                                LoginUtilities.print("Amount added to receivers Account Successfully");
                                ref = 1;
                            } else {
                                LoginUtilities.print(" Insufficient balance:");
                                LoginUtilities.print("If you want to go back enter 1 : ");
                                int z = keyboard.nextInt();
                                if (z == 1) {
                                    ref = 1;
                                }
                            }
                        } else {
                            LoginUtilities.print("Amount must be more than 1.99999 lakhs");ref=1;
                        }
                    } else {
                        LoginUtilities.print("your account type is not current account...");
                        ref = 1;
                    }

                } while (ref == 0);
            } else {
                LoginUtilities.print("No account exist with Receivers AccountNumber: " + accountNumber1);
            }
        }

        LoginUtilities.screenRefresh(7);
    }

    // Function to genarate account number
    public long generateAccNo() {
        long servicecode = 62618310;
        servicecode = servicecode * 100;
        servicecode = servicecode + ut.accGenarateCode;
        ut.accGenarateCode++;
        return servicecode;
    }

    // function to genarate CIF number
    public long generateCIFno() {
        long servicecode = 216261831;
        servicecode = servicecode * 100;
        servicecode = servicecode + ut.cifGenarateCode;
        ut.cifGenarateCode++;
        return servicecode;
    }
}
