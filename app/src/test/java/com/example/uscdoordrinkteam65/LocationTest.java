package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class LocationTest {

    @Test
    public void addToMenu() {
        ArrayList<Drink> menu = new ArrayList<>();
        menu.add(new Drink(10, "smoothie", 5.50));
        Drink d = new Drink(120, "green tea", 10.00);
        Location l = new Location("BobaTime", 78.1, 89.209, menu);
        ArrayList<Drink> menu2 = new ArrayList<>(menu);
        menu2.add(d);
        l.addToMenu(d);
        assertEquals(menu2, l.getMenu());
    }

    @Test
    public void getName() {
        Location l = new Location("BobaTime", 78.1, 89.209, null);
        assertEquals(l.getName(), "BobaTime");
    }

    @Test
    public void getLatitude() {
        Location l = new Location("BobaTime", 78.1, 89.209, null);
        assertEquals(l.getLatitude(), 78.1, 0);
    }

    @Test
    public void getLongitude() {
        Location l = new Location("BobaTime", 78.1, 89.209, null);
        assertEquals(l.getLongitude(), 89.209, 0);
    }

    @Test
    public void getMenu() {
        ArrayList<Drink> menu = new ArrayList<>();
        menu.add(new Drink(10, "smoothie", 5.50));
        Location l = new Location("BobaTime", 78.1, 89.209, menu);
        assertEquals(menu, l.getMenu());

    }
    @Test
    public void Location() {
        Location l = new Location();
        assertNotNull(l);
    }
}