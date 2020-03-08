package com.sushant.contactapplication;

public class UserData {
    private String fname;
    private String lname;
    private String number;

    public UserData(String fname, String lname, String number) {
        this.fname = fname;
        this.lname = lname;
        this.number = number;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getNumber() {
        return number;
    }
}
