package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.time.LocalDate;

public class OrderTest {

    @Test
    public void addToOrder() {
        Order o = new Order();
        assertEquals(o.getDrinks().size(), 0);
        Drink d = new Drink(100,"Tea",2);
        o.addToOrder(d);
        assertEquals(o.getDrinks().get(0), d);
    }

    @Test
    public void resetOrder() {
        Order o = new Order();
        Drink d = new Drink(100,"Tea",2);
        o.addToOrder(d);
        o.resetOrder();
        assertEquals(o.getDrinks().size(), 0);


    }



    @Test
    public void getDate() {
        Order o = new Order();
        assertEquals(o.getDate(), LocalDate.now().toString());
    }

    @Test
    public void addSupplementalInfo() {
        Order o = new Order();
        o.addSupplementalInfo(15.00, 430);
        assertEquals(o.getTotalCost(), 15.00,0);
        assertEquals(430, o.getCaffeineTotal());
    }


}