package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class GlobalVarsTest {

    @Test
    public void LoadTestUser(){
        Customer dummy = new Customer("Test", "1", new Order(), new ArrayList<>());
        GlobalVars.LoadTestUser();
        assertEquals(GlobalVars.currCustomer.getName(), dummy.getName());
    }
    @Test
    public void currCustomer(){
        Customer dummy = new Customer("Test", "1", new Order(), new ArrayList<>());
        GlobalVars.currCustomer = dummy;
        assertNotNull(GlobalVars.currCustomer);
        assertEquals(GlobalVars.currCustomer.getName(), dummy.getName());
    }
    @Test
    public void currSeller(){
        Seller dummy = new Seller("test", "12345", "BobaTime");
        GlobalVars.currSeller = dummy;
        assertNotNull(GlobalVars.currSeller);
        assertEquals(GlobalVars.currSeller.getName(), dummy.getName());
    }
    @Test
    public void selectedLocation(){
        ArrayList<Drink> menu = new ArrayList<>();
        menu.add(new Drink(5, "coffee", 4.50));
        Location dummy = new Location("BobaTime", 93.10, 79.249, menu);
        GlobalVars.selectedLocation = dummy;
        assertNotNull(GlobalVars.selectedLocation);
        assertEquals(GlobalVars.selectedLocation.getName(), dummy.getName());
    }
    @Test
    public void currentIsSeller(){
       GlobalVars.currentIsSeller = true;
        assertNotNull(GlobalVars.currentIsSeller);
        assertEquals(GlobalVars.currentIsSeller, true);
    }
    @Test
    public void currentEta(){
        GlobalVars.currentEta = 50;
        assertNotNull(GlobalVars.currentEta);
        assertEquals(GlobalVars.currentEta, 50);
    }
}
