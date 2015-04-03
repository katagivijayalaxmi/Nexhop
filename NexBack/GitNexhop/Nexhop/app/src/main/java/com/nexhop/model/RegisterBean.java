package com.nexhop.model;

/**
 * Created by vijayalaxmi on 16/3/15.
 */


public class RegisterBean {
    public String Name;
    public String lName;
    public String phNumber;
    public String emailId;
    public String password;


    public String getName() {
        return Name;
    }

    public void setName(String fN0ame) {
        this.Name = Name;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhoneNo() {
        return phNumber;
    }

    public void setPhoneNo(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
