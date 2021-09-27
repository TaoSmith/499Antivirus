package com.abc.accounts;

import java.util.*;

public class User {
    private String fname;
    private String lname;
    private String username;
    private String password;
    private ArrayList<ScanHistory> scanHistory;

    public User(String username, String password, String fname, String lname) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.scanHistory = new ArrayList<>();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addScan(ScanHistory scan) {
        scanHistory.add(scan);
    }

    public void scanHistory() {
        System.out.println("SCAN HISTORY:");
        for(int i = 0; i < scanHistory.size(); i++) {
            System.out.println(scanHistory.get(i).toString());
        }
    }

}
