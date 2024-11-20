package com.example.uscdoordrinkteam65;

/**
 * Class for sellers and customers to inherit from
 * */
public class User {
    private String name;
    private String userID;
    private boolean isSeller;
    public User(String name, String userID, boolean isSeller) {
        this.name = name;
        this.userID = userID;
        this.isSeller = isSeller;
    }
    public User() {}
    /*public void setIsSeller(boolean seller) {
        isSeller = seller;
    }*/
    public String getName(){
        return name;
    }
    public String getUserID() {
        return userID;
    }

    public boolean isSeller() {
        return isSeller;
    }
    public void setSeller() {
        isSeller = true;
    }
}
