package src.userinterface;

import java.time.LocalDate;
import java.util.Scanner;
import src.entities.Account;
import src.entities.CIF;
import src.entities.FixedDeposit;
import src.entities.Loan;
import src.utils.LoginUtilities;
import src.utils.Utilties;
import src.services.*;

public class BankUI {
    LoanServices lo = new LoanServices();
    private Scanner keyboard = new Scanner(System.in);;
    AccountDriver ad = new AccountDriver();
    AccountDriverUI adi = new AccountDriverUI();
    Utilties ut = Utilties.getInstance();
    LoanServicesUI lsi = new LoanServicesUI();
    FixedDepositServices fds = new FixedDepositServices();
    Bank b = new Bank();
    FixedDepositServicesUI fdsi = new FixedDepositServicesUI();

    // this function is used to apply for Fixed Deposit Account
    public FixedDeposit createFDUI(CIF cifs[], int cifindex, FixedDeposit fd[], Account accounts[], long accountNumber,double amount,
            Scanner keyboard) {
        FixedDeposit fda = null;
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        fda = b.createFD(cifs, cifindex, fd, accounts, accountNumber, index, amount, keyboard);
        // cifs[cindex].cifList.add(new CIF(cifs[cifindex].getcifno(), accountNumber,
        // accountType, balanceType))
        if (fda != null) {
            System.out.println("Fixed Deposit Account loading...");
            // fdsi.getFDBalances(fd, fds.searchFDAcc(fd, ut.lindex, accountNumber),
            // accounts, index);
        } else
            System.out.println("Fixed Deposit Account is not created..");

        return fda;
    }

    // this function is used to apply for Fixed Deposit Account
    public void applyFDAccUI(CIF cifs[], int cifindex, Account accounts[], FixedDeposit fd[], long accountNumber,
            Scanner keyboard) {
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        if (accounts[index].getBalance() > 0) {
            long accountNumber1 = checkFDAcc(fd, accounts, accountNumber, index, keyboard);
            int findex = fds.searchFDAcc(fd, FixedDeposit.fDindex, accountNumber1);
            System.out.println("You have applied for fixed deposit please wait...");
            double amount = getFDAmount(cifs, cifindex, accounts, ut.numAccounts, accountNumber, index,
                    keyboard);
            if (findex > -1) {
                if (fd[findex].available == false) {
                    fd[findex] = createFDUI(cifs, cifindex, fd, accounts, accountNumber1,amount, keyboard);
                    b.applyFDAcc(cifs, cifindex, accounts, fd, accountNumber, accountNumber1, index, findex,amount, keyboard);
                }
            } else if (accountNumber1 != -1) {
                fd[FixedDeposit.fDindex++] = createFDUI(cifs, cifindex, fd, accounts, accountNumber1,amount, keyboard);
                findex = FixedDeposit.fDindex - 1;
                b.applyFDAcc(cifs, cifindex, accounts, fd, accountNumber, accountNumber1, index, findex,amount, keyboard);
            }
        } else
            System.out.println("Account balance is 0...\nPlease do some deposit as per required..");
    }

    // this function is used to withdraw Amount from the custom Fixed Deposit
    // Account
    public void dowithdrawFDAmtUI(FixedDeposit fd[], Account accounts[], long accountNumber, Scanner keyboard) {
        int findex = fds.searchFDAcc(fd, FixedDeposit.fDindex, accountNumber);
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        if (index >= 0) {
            if (findex >= 0) {
                if (fd[findex].available) {
                    if (LoginUtilities.login(accounts, ut.numAccounts, accountNumber, keyboard))
                        b.dowithdrawFDAmt(fd, accounts, accountNumber, index, findex, keyboard);
                    else
                        System.out.println("invalid user password ..!");
                } else
                    System.out.println("Your Fixed Deposit Amount already cleared....");
            } else
                System.out.println("You need to create Fixed Deposit Account...");
        } else
            System.out.println("invalid user account number....");
    }

    // this function is used to validate the custom Fixed deposit Account user
    public long checkFDAcc(FixedDeposit fd[], Account accounts[], long accountNumber, int index, Scanner keyboard) {
        int lindex = fds.searchFDAcc(fd, FixedDeposit.fDindex, accountNumber);
        if (index >= 0) {
            if (lindex == -1) {
                if (LoginUtilities.login(accounts, ut.numAccounts, accountNumber, keyboard))
                    return accountNumber;
                else
                    System.out.println("invalid user account number ....!");
            } else if (fd[lindex].available != true) {
                System.out.println(
                        "Your Fixed Deposit Amount Successfully Clear....\nYou can create new Fixed Deposit Account....");
                return accountNumber;
            } else {
                System.out.println("You had already created Fixed Deposit Account...");
                lindex = -1;
                return lindex;
            }
        } else
            System.out.println("invalid user account number...");
        return -1;
    }

    // This function is used to get Fixed Deposit Amount
    public double getFDAmount(CIF cifs[], int cifindex, Account accounts[], int count, long accountNumber, int index,
            Scanner keyboard) {
        int x = 1;
        double amount;
        do {
            amount = fdsi.getAmount(accounts, index, keyboard);
            if (amount <= accounts[index].getBalance()) {
                x = 0;
            } else {
                System.out.println(
                        "Account balance is less than Fixed deposit Amount..\nDeposit some required amount to your account ");
                adi.doDepositUI(cifs, cifindex, accounts, count, accountNumber, keyboard);
            }
        } while (x == 1);
        return amount;
    }

    // this function is used to apply loan
    public void applyLoanAcc(Loan loans[], Account accounts[], long accountNumber, Scanner keyboard) {
        int index = lo.searchloanAcc(loans, Loan.loanindex, accountNumber);
        long accountNumber1 = checkLoanAcc(loans, accounts, accountNumber, index, keyboard);
        if (index > -1) {
            if (!loans[index].available)
                loans[index] = createLoanAccUI(loans, accounts, accountNumber, keyboard);
        } else if (accountNumber1 != -1) {
            loans[Loan.loanindex++] = createLoanAccUI(loans, accounts, accountNumber1, keyboard);
        }
    }

    // this function is used to validate the custom loan Account
    public long checkLoanAcc(Loan loans[], Account accounts[], long accountNumber, int lindex, Scanner keyboard) {
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        if (index >= 0) {
            if (lindex == -1) {
                if (LoginUtilities.login(accounts, ut.numAccounts, accountNumber, keyboard))
                    return accountNumber;
                else
                    System.out.println("invalid user credentials ....!");
            } else if (!loans[lindex].available) {
                System.out.println("Your Loan Amount Successfully Clear....\nYou can Apply for new Loan....");
                return accountNumber;
            } else {
                System.out.println("You had already took loan...");
                lindex = -1;
                return lindex;
            }
        } else
            System.out.println("invalid user account number...");
        return -1;
    }

    //this function is used to create Loan Account
    public Loan createLoanAccUI(Loan loans[], Account accounts[], long accountNumber, Scanner keyboard) {
        System.out.println("You applied for loan wait for responce...");
        Loan lao = null;
        lao = b.createLoanAcc(loans, accounts, accountNumber, keyboard);
        if (lao != null) {
            System.out.println("Loan Account is created Successfully...");
        } else {
            System.out.println("Loan Account is not created...");
        }
        return lao;
    }

    //this function is used to create CIF
    public CIF docreateCIFUI(CIF cifs[], long adharno) {
        System.out.print("Enter username      :");
        String username = keyboard.next();
        int age = getUserAge(keyboard);
        long mobileno = LoginUtilities.getMobileNo(keyboard);
        long cifno = ad.generateCIFno();
        CIF cifac = b.docreateCIF(cifs, adharno, username, age, mobileno, cifno);
        if (cifac != null) {
            System.out.println("Successfully CIF created....");
        } else {
            System.out.println("CIF is not created");
        }
        return cifac;
    }

    // this function is used to pay the custom loan Account Balance
    public void doPayLoanAmt(Loan loans[], Account accounts[], long accountNumber, Scanner keyboard) {
        int lindex = lo.searchloanAcc(loans, Loan.loanindex, accountNumber);
        int index = LoginUtilities.searchAccount(accounts, ut.numAccounts, accountNumber);
        if (index >= 0) {
            if (lindex >= 0) {
                if (loans[lindex].available) {
                    if (LoginUtilities.login(accounts, ut.numAccounts, accountNumber, keyboard))
                        lsi.payLoanAmount(loans, accounts, accountNumber, lindex, index, loans[lindex].loanAmount,
                                keyboard);
                    else
                        System.out.println("invalid user password ..!");
                } else
                    System.out.println("Your loan Amount Successfully paid....");
            } else
                System.out.println("You need to take loan...");
        } else
            System.out.println("invalid user account number....");
    }

    //this function is used to get adhar number from user
    public long getAdharno(Scanner keybord) {
        long adharno = LoginUtilities.getAdharno(keyboard);
        return adharno;
    }

    //this function is used to get Account number from user
    public long getAccNumber(Scanner keyboard) {
        long accountNumber = LoginUtilities.getAccNumber(keyboard);
        return accountNumber;
    }

    //this function is used to create Account
    public Account createAccountUI(CIF cifs[], int cifindex, Account accounts[], Scanner keyboard) {
        String accType = accountMenu(keyboard);
        System.out.print("Enter the password  :");
        String password = keyboard.next();
        long accountNumber = ad.generateAccNo();
        LocalDate accOpenDate = LocalDate.now();
        String balType = balancetype(keyboard);
        Account acc = b.createAccount(cifs, cifindex, accounts, accType, password, accountNumber, accOpenDate, balType);
        if (acc != null) {
            userPassbook(cifs[cifindex].getcifno(), cifs[cifindex].getUsername(), cifs[cifindex].getAge(),
                    accountNumber, cifs[cifindex].getMobileno(), accOpenDate, accType, balType);
        } else {
            System.out.println("Account is not created");
        }
        return acc;
    }

    // this function is used to Display User Account details
    public void userPassbook(long cifno, String username, int age, long accountNumber, long mobileno,
            LocalDate accOpenDate, String accType, String balType) {
        System.out.println("  ----    Passbook    ----");
        System.out.println(" CIF No          : " + cifno);
        System.out.println(" Username        : " + username);
        System.out.println(" Age             : " + age);
        System.out.println(" Account number  : " + accountNumber);
        System.out.println(" Account Type    : " + accType);
        System.out.println(" Balance Type    : " + balType);
        System.out.println(" Mobile Number   : " + mobileno);
        System.out.println(" Account opened on " + accOpenDate);
        LoginUtilities.screenRefresh(5);
    }

    // displays types of account
    public static String accountMenu(Scanner keyboard) {
        System.out.println("--------------------------------------------------------");
        System.out.println("Select Account Type");
        System.out.println("1. Current Account");
        System.out.println("2. Savings Account");
        System.out.println("--------------------------------------------------------");
        int choice = 0;
        String accType;
        do {
            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(keyboard.next());
            } catch (NumberFormatException e) {
                System.out.println("entered choice  is not a valid integer number");
            }

            if (choice == 1)
                accType = "CurrentAccount";
            else
                accType = "SavingsAccount";
        } while (choice < 1 || choice > 2);

        return accType;
    }

    // this function is used to choose the Balance Type
    public String balancetype(Scanner keyboard) {
        int choice = 0;
        String balanceType;
        do {
            System.out.println("Choose the Balance Type : ");
            System.out.println("1. Zero Account Balance ");
            System.out.println("2. Minimum balance(2000)");
            try {
                choice = Integer.parseInt(keyboard.next());
            } catch (NumberFormatException e) {
                System.out.println("entered choice is not a valid integer number");
            }
            if (choice == 2) {
                balanceType = "MinimumBalanceAccount";
                return balanceType;
            } else {
                balanceType = "ZeroBalanceAccount";
            }
        } while (choice < 1 || choice > 2);
        return balanceType;
    }

    // this function is used to get user Age above 9
    public static int getUserAge(Scanner keyboard) {
        int age;
        do {
            do {
                try {
                    System.out.print("Enter Age           :");
                    age = Integer.parseInt(keyboard.next());
                } catch (Exception e) {
                    System.out.println("\nentered  is not a valid integer number\n Enter age :");
                    age = 0;
                }
            } while (age == 0);
            if (age >= 10 && age <= 100)
                break;
            else
                System.out.println("Enter age above 9 and below 100 only");
        } while (true);
        return age;
    }
}
