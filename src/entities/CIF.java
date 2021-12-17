package src.entities;

import java.util.ArrayList;

// CIF means Customer Informatiion File.
//This consits all information about the user.This is unique for each user.
public class CIF {
    public static int cifindex = 2;
    private String username;
    private long mobileno;
    private int age;
    private long cifno;
    public long accountNumber;
    private long adharno;
    public String accountType;
    public String balanceType;

    public CIF() {

    }

    public CIF(long cifno, String username, long mobileno, int age, long adharno) {
        this.cifno = cifno;
        this.username = username;
        this.mobileno = mobileno;
        this.age = age;
        this.adharno = adharno;
    }

    public CIF(long cifno, long accountNumber, String accountType, String balanceType) {
        this.cifno = cifno;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balanceType = balanceType;
    }

    // getters and setters
    public int getAge() {
        return this.age;
    }

    public String getUsername() {
        return this.username;
    }

    public void setMobileno(long mobileno) {
        this.mobileno = mobileno;
    }

    public long getadharno() {
        return adharno;
    }

    public long getMobileno() {
        return mobileno;
    }

    public long getcifno() {
        return cifno;
    }

    // this arraylist is used to store the user accounts details
    public ArrayList<CIF> cifList = new ArrayList<CIF>();
}
