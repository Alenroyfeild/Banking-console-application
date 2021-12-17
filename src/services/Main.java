package src.services;

import java.time.LocalDate;
import java.util.Scanner;

import src.entities.Account;
import src.entities.CIF;
import src.entities.CurrentAccount;
import src.entities.FixedDeposit;
import src.entities.Loan;
import src.entities.MiniStatement;
import src.entities.SavingsAccount;
import src.userinterface.AccountDriverUI;
import src.userinterface.BankUI;
import src.userinterface.Notifications;
import src.userinterface.UserProfile;
import src.utils.LoginUtilities;
import src.utils.Utilties;

public class Main {
    public static int choicecif(Scanner keyboard) {
        System.out.println("---------------------------------------------------------------\n");
        System.out.println("  --  Welcome  --");
        System.out.println("1.Create new Account(CIF):");
        System.out.println("2.Existing user login");
        System.out.println("3.Quit");
        int button = 0;
        do {
            System.out.print("Enter choice: ");
            try {
                button = Integer.parseInt(keyboard.next());
            } catch (NumberFormatException e) {
                System.out.println("entered choice is not a valid integer number");
            }
        } while (button < 1 || button > 3);

        return button;
    }

    // displays welcome page
    public static int firstMenu(Scanner keyboard) {
        System.out.println("---------------------------------------------------------------\n");
        System.out.println("   ---   Welcome Page   ---");
        System.out.println("1. Create new Account");
        System.out.println("2. User Login");
        System.out.println("3. Forgot Password ");
        System.out.println("4. Quit");
        System.out.println("---------------------------------------------------------------\n");
        int button = 0;
        do {
            System.out.print("Enter choice: ");
            try {
                button = Integer.parseInt(keyboard.next());
            } catch (NumberFormatException e) {
                System.out.println("entered choice is not a valid integer number");
            }
        } while (button < 1 || button > 4);

        return button;

    }

    // displays bank menu list
    public static int menu(CIF cifs[], int cifindex, Account accounts[], long accountNumber, int index,
            Scanner keyboard) {
        Notifications.displayNotificationList(cifs, cifindex, accounts, accountNumber);
        int choice = 0;
        if (accounts[index].accType.equals("SavingsAccount")) {
            System.out.println("---------------------------------------------------------------\n");
            System.out.println("  --  Bank Account Menu  --\n");
            System.out.println("  1. Deposit Funds");
            System.out.println("  2. Withdraw Funds");
            System.out.println("  3. Interest amount");
            System.out.println("  4. balance enquiry");
            System.out.println("  5. User Account Profile");
            System.out.println("  6. Amount Transfer");
            System.out.println("  7. Apply Loan");
            System.out.println("  8. Pay Loan Amount");
            System.out.println("  9. Create Fixed Deposit Account");
            System.out.println("  10.withdraw fixed Deposit");
            System.out.println("  11.Mini Statement");
            System.out.println("  12.Quit");
            System.out.println("---------------------------------------------------------------");
            do {
                System.out.print("\n  Enter choice : ");
                try {
                    choice = Integer.parseInt(keyboard.next());
                } catch (NumberFormatException e) {
                    System.out.println("entered choice  is not a valid integer number");
                }
            } while (choice < 1 || choice > 12);

        } else {
            System.out.println("---------------------------------------------------------------\n");
            System.out.println("  --  Bank Account Menu  --\n");
            System.out.println("  1. Deposit Funds");
            System.out.println("  2. Withdraw Funds");
            System.out.println("  4. balance enquiry");
            System.out.println("  5. User Account Profile");
            System.out.println("  6. Amount Transfer");
            System.out.println("  7. Apply Loan");
            System.out.println("  8. Pay Loan Amount");
            System.out.println("  9. Create Fixed Deposit Account");
            System.out.println("  10.withdraw fixed Deposit");
            System.out.println("  11.Mini Statement");
            System.out.println("  12.Quit");
            System.out.println("---------------------------------------------------------------");

            do {
                System.out.print("\n  Enter choice : ");
                try {
                    choice = Integer.parseInt(keyboard.next());
                    if (choice == 3) {
                        System.out.println("Option is not present here..\nPlease choose other option...");
                        choice = 0;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("entered choice  is not a valid integer number");
                }
            } while (choice < 1 || choice > 12);
        }
        return choice;
    }

    // entry point of the program
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        CIF cifs[] = new CIF[10];
        cifs[0] = new CIF(21626183101l, "balaji", 9701660809l, 21, 622988081663l);
        cifs[1] = new CIF(21626183102l, "royal", 9701660809l, 21, 622988081664l);
        // Create array of Accounts
        Account accounts[] = new Account[10];
        accounts[0] = new CurrentAccount(6261831001l, "balaji", LocalDate.of(2020, 01, 01), "CurrentAccount",
                "ZeroBalanceAccount");
        accounts[1] = new SavingsAccount(6261831002l, "royal", LocalDate.of(2021, 05, 01), "SavingsAccount",
                "MinimumBalanceAccount");
        accounts[2] = new CurrentAccount(6261831003l, "balu", LocalDate.of(2021, 01, 01), "CurrentAccount",
                "MinimumBalanceAccount");
        accounts[1].setBalance(1000);
        // Create array of loan Accounts
        Loan loans[] = new Loan[10];
        loans[0] = new Loan(6261831001l, LocalDate.of(2021, 01, 01), 50000, 10, 12, 12, true);
        accounts[0].setBalance(50000);
        accounts[0].miniStatements.add(new MiniStatement("loanDeposit", LocalDate.of(2021, 01, 01), 50000));
        loans[1] = new Loan(6261831002l, LocalDate.of(2021, 05, 01), 90000, 10, 12, 12, true);
        accounts[1].setBalance(150000);
        accounts[1].miniStatements.add(new MiniStatement("loanDeposit", LocalDate.of(2021, 05, 01), 90000));
        UserProfile user = new UserProfile();
        // Create array of Fixed Deposit Accounts
        FixedDeposit fd[] = new FixedDeposit[10];
        Utilties ut = Utilties.getInstance();
        fd[0] = new FixedDeposit(6261831003l, LocalDate.of(2021, 01, 01), 10000, 11, 335, 7.50f, true);
        cifs[0].cifList.add(new CIF(21626831001l, 6261831001l, "CurrentAccount", "ZeroBalanceAccount"));
        cifs[0].cifList.add(new CIF(21626831001l, 6261831002l, "SavingAccount", "ZeroBalanceAccount"));
        cifs[1].cifList.add(new CIF(216261831002l, 6261831003l, "CurrentAccount", "MinimumBalanceAccount"));
        cifs[0].cifList.add(new CIF(216261831001l, 6261831002l, "loanAccount", ""));
        BankUI bi = new BankUI();
        Bank b=new Bank();
        AccountDriverUI ad = new AccountDriverUI();
        AccountDriver ad1=new AccountDriver();
        int choice, button, button1;
        do {
            button1 = choicecif(keyboard);
            if (button1 == 1) {
                long adharno =bi.getAdharno(keyboard);
                boolean check =b.checkCIF(cifs, CIF.cifindex, adharno, keyboard);
                if (!check) {
                    cifs[CIF.cifindex++] =bi.docreateCIFUI(cifs, adharno);
                }
                int cifindex =b.searchCIFadharno(cifs, CIF.cifindex, adharno);
                accounts[ut.numAccounts++] =bi.createAccountUI(cifs, cifindex, accounts, keyboard);
            } else if (button1 == 2) {
                long adharno =bi.getAdharno(keyboard);
                int cifindex =b.searchCIFadharno(cifs, CIF.cifindex, adharno);
                if (cifindex >= 0) {
                    do {
                        button = firstMenu(keyboard);
                        if (button == 1) {
                            accounts[ut.numAccounts++] =bi.createAccountUI(cifs, cifindex, accounts,
                                    keyboard);
                        } else if (button == 2) {
                            long accountNumber =bi.getAccNumber(keyboard);
                            Boolean checkAcc =b.searchCIFAccno(cifs, cifindex, accountNumber);
                            if (checkAcc) {
                                if (LoginUtilities.login(accounts, ut.numAccounts, accountNumber, keyboard)) {
                                    do {
                                        choice = menu(cifs, cifindex, accounts, accountNumber,
                                                LoginUtilities.searchAccount(
                                                        accounts, ut.numAccounts, accountNumber),
                                                keyboard);
                                        Notifications.autoPayLoanEMI(accounts, accountNumber, loans);
                                        if (choice == 1) {
                                            ad.doDepositUI(cifs, cifindex, accounts, ut.numAccounts,
                                                    accountNumber,
                                                    keyboard);
                                        } else if (choice == 2) {
                                            ad.doWithdrawUI(cifs, cifindex, accounts, ut.numAccounts,
                                                    accountNumber,
                                                    keyboard);
                                        } else if (choice == 3) {
                                            ad.applyInterestUI(cifs, cifindex, accounts, ut.numAccounts,
                                                    accountNumber, keyboard);
                                        } else if (choice == 4) {
                                            ad1.doBalanceEnquiry(accounts, ut.numAccounts, accountNumber,
                                                    keyboard);
                                        } else if (choice == 5) {
                                            user.userAccount(cifs, cifindex, accounts, loans, fd,
                                                    ut.numAccounts,
                                                    accountNumber, keyboard);
                                        } else if (choice == 6) {
                                            ad.amountTransferUI(cifs, cifindex, accounts, ut.numAccounts,
                                                    accountNumber, keyboard);
                                        } else if (choice == 7) {
                                           bi.applyLoanAcc(loans, accounts, accountNumber, keyboard);
                                        } else if (choice == 8) {
                                           bi.doPayLoanAmt(loans, accounts, accountNumber, keyboard);
                                        } else if (choice == 9) {
                                           bi.applyFDAccUI(cifs, cifindex, accounts, fd, accountNumber, keyboard);
                                        } else if (choice == 10) {
                                           bi.dowithdrawFDAmtUI(fd, accounts, accountNumber, keyboard);
                                        } else if (choice == 11) {
                                            ad.dominiStatementsUI(accounts, ut.numAccounts, accountNumber,
                                                    keyboard);
                                        } else {
                                            System.out.println("Thank You...!");
                                        }
                                    } while (choice != 12);
                                } else {
                                    System.out.println("invalid username or password..!");
                                }
                            } else {
                                System.out.println("Invalid Account Number..!");
                            }
                        } else if (button == 3) {
                            LoginUtilities.forgotPassword(cifs, cifindex, accounts, ut.numAccounts,
                                    keyboard);
                        } else {
                            System.out.println("Good Bye..!");
                        }

                    } while (button != 4);
                } else {
                    System.out.println("invalid adhar number....");
                }
            } else {
                System.out.println("Thank You.....!");
            }
        } while (button1 != 3);
    }
}
