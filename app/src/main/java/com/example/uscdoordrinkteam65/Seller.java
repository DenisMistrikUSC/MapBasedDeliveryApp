package com.example.uscdoordrinkteam65;

import java.util.ArrayList;

public class Seller extends User {
//    private Store s;
//    public Seller(String name, String userID) {
//        super(name, userID, true);
//    }
    private String storeName;
    public Seller() {}
    public Seller(String name, String userID, String s) {
        super(name, userID, true);
        if (s == null) {
            this.storeName = "";
        }
       else {
           this.storeName = s;
        }
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String s) {
        storeName = s;
    }
//    public void setStore(Store store) {
//        s = store;
//    }
//    public boolean checkIfStoreExists() {
//        return s != null;
//    }

}
