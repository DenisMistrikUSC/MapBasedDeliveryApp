package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class CustomerTest {

    @Test
    public void checkOutCurrentOrder() {
//        Customer c = new Customer()
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToOrder(d);
        Order o = c.getCurrentOrder();
//        ArrayList<Order> checker = new ArrayList<>();
//        checker.add(o);
        c.checkOutCurrentOrder();
        Drink test = c.getHistory().get(0).getDrinks().get(0);
        assertEquals(test.getName(),d.getName());
        assertEquals(test.getPrice(),d.getPrice(), 0);
        assertEquals(test.getCaffeine(),d.getCaffeine());
        assertEquals(c.getNumOrders(),1);

    }

    @Test
    public void addToOrder() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToOrder(d);
        Order o = c.getCurrentOrder();
        ArrayList<Drink> check = o.getDrinks();
        assertEquals(check.get(0).getName(), "Tea");
    }

    @Test
    public void updateCaffeineIntake() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToOrder(d);
        c.updateCaffeineIntake();
        assertEquals(c.getTotalCaffeineIntake(), 100);
    }

    @Test
    public void getCurrentOrder() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToOrder(d);
        c.updateCaffeineIntake();
        Order o = c.getCurrentOrder();
        ArrayList<Drink> check = new ArrayList<>();
        check.add(d);
        assertEquals(o.getDrinks(),check);
    }

    @Test
    public void checkIfOverLimit() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToOrder(d);
        c.updateCaffeineIntake();
        assertEquals(c.checkIfOverLimit(),false);
    }

    @Test
    public void addToCaffeine() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToCaffeine(100);
        assertEquals(c.getTotalCaffeineIntake(), 100);
    }

    @Test
    public void resetCaffeineIntake() {
        Customer c = new Customer();
        c.addToCaffeine(100);
        c.resetCaffeineIntake();
        assertEquals(c.getTotalCaffeineIntake(), 0);
    }
    @Test
    public void checkCanHaveDrink() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToCaffeine(100);
        assertEquals(c.checkCanHaveDrink(d), true);
        c.addToCaffeine(310);
        assertFalse(c.checkCanHaveDrink(d));

    }

    @Test
    public void getTotalCaffeineIntake() {
        Customer c = new Customer();
        c.addToCaffeine(100);
        assertEquals(c.getTotalCaffeineIntake(), 100);
    }

    @Test
    public void getNumOrders() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer();
        c.addToOrder(d);
        Order o = c.getCurrentOrder();
        assertEquals(c.getNumOrders(),0);
    }

    @Test
    public void getHistory() {
        Drink d = new Drink(100,"Tea",2);
        Customer c = new Customer("eszter", "12345", null, null);
        c.addToOrder(d);
        Order o = c.getCurrentOrder();
        ArrayList<Order> checker = new ArrayList<>();
        checker.add(o);
        assertEquals(o,checker.get(0));
    }
}