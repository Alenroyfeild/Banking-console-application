package src.utils;

import java.time.LocalDate;
import java.util.*;

import src.entities.Account;
import src.entities.CIF;

public final class LoginUtilities {
    static int ref = 0;
    private LoginUtilities() {

    }

     public static void print(String s){
        System.out.println(s);
    }  
    // this function is used to get Account number
    public static long getAccNumber(Scanner keyboard) {
        long accountNumber;
        do {
            do{
                try{
                    System.out.print("Enter the Account number : ");
                    accountNumber=Long.parseLong(keyboard.next());
                }catch(NumberFormatException e){
                    System.out.println("Entered Account number is not a valid integer number..");
                    accountNumber=0;
                }
            }while(accountNumber==0);
            if (Math.floor(Math.log10(accountNumber) + 1) == 10)
                ref = 1;
            else
                System.out.println("Enter the valid 10 digit account number:");
        } while (ref == 0);
        ref = 0;
        return accountNumber;
    }

    //this function is used to get adhar number
    public static long getAdharno(Scanner keyboard) {
        long adharno;
        do {
            do{
                try{
                    System.out.print("Enter the Adhar number : ");
                    adharno=Long.parseLong(keyboard.next());
                }catch(NumberFormatException e){
                    System.out.println("Entered Adharno is not a valid integer number..");
                    adharno=0;
                }
            }while(adharno==0);
            if (Math.floor(Math.log10(adharno) + 1) == 12)
                ref = 1;
            else
                System.out.println("Enter the valid 12 digit Adhar number:");
        } while (ref == 0);
        ref = 0;
        return adharno;
    }

    //this function is used to get CIF no
    public static long getCIFno(Scanner keyboard) {
        long adharno;
        do {
            do{
                try{
                    System.out.print("Enter the CIF number : ");
                    adharno=Long.parseLong(keyboard.next());
                }catch(NumberFormatException e){
                    System.out.println("Entered CIF is not a valid integer number..");
                    adharno=0;
                }
            }while(adharno==0);
            if (Math.floor(Math.log10(adharno) + 1) == 10)
                ref = 1;
            else
                System.out.println("Enter the valid 10 digit CIF number:");
        } while (ref == 0);
        ref = 0;
        return adharno;
    }

    // this function is used to check user credentials
    public static int checkAccNumber(Account accounts[], int count, long accountNumber, Scanner keyboard) {
        if (login(accounts, count, accountNumber, keyboard)) {
            // search for account
            int index = searchAccount(accounts, count, accountNumber);
            return index;
        } else {
            System.out.println("You entered wrong password");
            return -1;
        }
    }

    // this function is used to search the Account number
    public static int searchAccount(Account accounts[], int count, long accountNumber) {
        for (int i = 0; i < count; i++)
            if (accounts[i].getAccountNumber() == accountNumber)
                return i;
        return -1;
    }

    // this function is used to clear the screen
    public static void screenRefresh(int i) {
        // System.out.print("\033[H\033[2J");
        System.out.println("------------------------------------------------------------------");
        System.out.println("\n       -----     Zoho Bank     -----\n");
    }

    // this function is used to get mobileno
    public static long getMobileNo(Scanner keyboard) {
        long mobileno;
        do {
            do{
                try{
                    System.out.print("Enter Mobile Number : ");
                    mobileno=Long.parseLong(keyboard.next());
                }catch(NumberFormatException e){
                    System.out.println("Entered Mobileno is not a valid integer number..");
                    mobileno=0;
                }
            }while(mobileno==0);
            if (Math.floor(Math.log10(mobileno) + 1) == 10)
                ref = 1;
            else
                System.out.println("Enter the valid 10 digit Mobile number:");
        } while (ref == 0);
        ref = 0;
        return mobileno;
    }

    //this function is get amount from user
    public static double getAmount(Scanner keyboard){
        double amount;
        do{
            try{
                amount=Double.parseDouble(keyboard.next());
            }catch(NumberFormatException e){
                System.out.println("Entered Amount is not a valid integer number..\nplease enter Amount: ");
                amount=0;
            }
        }while(amount==0);
        return amount;
    }

    LocalDate date;

    // this function is used to login with user credentials
    public static Boolean login(Account accounts[], int count, Long accountNumber, Scanner keyboard) {
        Utilties ut = Utilties.getInstance();
        System.out.print("Enter the password      : ");
        String password = keyboard.next();
        return authenciate(accounts, ut.numAccounts, accountNumber, password);
    }

    // this function i used to check the user credentials is valid or not
    public static Boolean authenciate(Account accounts[], int count, long accountNumber, String password) {
        for (int i = 0; i < count; i++)
            if (accounts[i].getAccountNumber() == accountNumber && accounts[i].checkPassword(password))
                return true;
        return false;

    }

    // this functoin is used to update the password
    public static void forgotPassword(CIF cifs[], int cifindex, Account accounts[], int count, Scanner keyboard) {
        screenRefresh(1);
        System.out.println("---     Login Page     ---\n");
        long accountNumber = getAccNumber(keyboard);
        long mobileno = getMobileNo(keyboard);
        long cifno = getCIFno(keyboard);
        for (int j = 0; j < cifindex; j++) {
            if (cifs[j].getcifno() == cifno && cifs[j].getMobileno() == mobileno) {
                for (int i = 0; i < count; i++) {
                    if (accounts[i].getAccountNumber() == accountNumber) {
                        System.out.print("Enter New Password:");
                        String password = keyboard.next();
                        accounts[i].setPassword(password);
                        System.out.println("\nYour Password Updated Successfully.....:");
                        return;
                    }
                }
                System.out.println("invalid user account number..!");
            } else {
                System.out.println("Mobile no or CIF no is invalid....");
            }
        }
    }
}