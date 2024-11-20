package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

public class SellerTest {

    @Test
    public void getStoreName() {
        Seller s = new Seller("eszter", "12345", "BobaTime");
        assertEquals(s.getStoreName(), "BobaTime");

    }

    @Test
    public void setStoreName() {
        Seller s = new Seller("eszter", "12345", "BobaTime");
        s.setStoreName("QWNCH");
        assertEquals(s.getStoreName(), "QWNCH");
    }
    @Test
    public void Seller() {
        Seller s = new Seller();
        assertNotNull(s);
    }
}