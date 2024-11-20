package com.example.uscdoordrinkteam65;

import java.util.ArrayList;

public class GlobalVars {
    //
    public static Location selectedLocation;
    public static Customer currCustomer;
    public static Seller currSeller;
    public static boolean currentIsSeller;
    public static int currentEta = -10;
    public static String currSelectedStoreName = "error";
    public static void LoadTestUser(){
        Customer dummy = new Customer("Test", "1", new Order(), new ArrayList<>());
        currCustomer = dummy;
    }
}
