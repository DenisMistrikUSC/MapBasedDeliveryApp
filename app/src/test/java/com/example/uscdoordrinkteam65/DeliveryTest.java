package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDate;

public class DeliveryTest {

    @Test
    public void getDuration() {
//        Delivery d = new Delivery()
        Delivery delivery = new Delivery(null, 30, "BobaTime");
        assertEquals(delivery.getDuration(), 30);
    }



    @Test
    public void getOrder() {
        Order o = new Order();
        Drink d = new Drink(100,"Tea",2);
        o.addToOrder(d);
        Delivery delivery = new Delivery(o, 30, "BobaTime");
        assertEquals(o, delivery.getOrder());
    }

    @Test
    public void getTime() {
        Delivery delivery = new Delivery(null, 30, "BobaTime");
        assertNotNull(delivery.getStartTime());
        assertNotNull(delivery.getEndTime());

    }

    @Test
    public void getDate() {
        Delivery delivery = new Delivery(null, 30, "BobaTime");
        assertEquals(delivery.getDate(), LocalDate.now().toString());
    }

    @Test
    public void getStoreName() {
        Delivery delivery = new Delivery(null, 30, "BobaTime");
        assertEquals(delivery.getStoreName(), "BobaTime");
    }
}