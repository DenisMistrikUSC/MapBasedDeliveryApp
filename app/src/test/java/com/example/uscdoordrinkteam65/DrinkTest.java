package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

public class DrinkTest {


    @Test
    public void getName() {
        Drink d = new Drink(50, "juice", 3.50);
        assertEquals(d.getName(), "juice");
    }

    @Test
    public void getCaffeine() {
        Drink d = new Drink(50, "juice", 3.50);
        assertEquals(d.getCaffeine(), 50);
    }

    @Test
    public void getPrice() {
        Drink d = new Drink(50, "juice", 3.50);
        assertEquals(3.50, d.getPrice(), 0.0);
    }

    @Test
    public void setName() {
        Drink d = new Drink(50, "juice", 3.50);
        d.setName("coffee");
        assertEquals(d.getName(), "coffee");
    }

    @Test
    public void setCaffeine() {
        Drink d = new Drink(50, "juice", 3.50);
        d.setCaffeine(200);
        assertEquals(d.getCaffeine(), 200);
    }

    @Test
    public void setPrice() {
        Drink d = new Drink(50, "juice", 3.50);
        d.setPrice(5.00);
        assertEquals(5.00, d.getPrice(), 0.0);
    }

    @Test
    public void addCount() {
        Drink d = new Drink(50, "juice", 3.50);
        d.addCount();
        assertEquals(d.getCount(), 2);
    }
    @Test
    public void Drink() {
        Drink d = new Drink();
        assertNotNull(d);

    }
}