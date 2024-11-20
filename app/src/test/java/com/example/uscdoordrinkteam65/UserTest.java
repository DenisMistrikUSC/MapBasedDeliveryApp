package com.example.uscdoordrinkteam65;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {


    @Test
    public void getName() {
        User u = new User("Eszter", "12345", false);
        assertEquals(u.getName(), "Eszter");
    }

    @Test
    public void getUserID() {
        User u = new User("Eszter", "12345", false);
        assertEquals(u.getUserID(), "12345");
    }

    @Test
    public void isSeller() {
        User u = new User("Eszter", "12345", false);
        assertFalse(u.isSeller());
        User u2 = new User("Eszter", "12345", true);
        assertTrue(u2.isSeller());
    }

    @Test
    public void setSeller() {
        User u = new User("Eszter", "12345", false);
        u.setSeller();
        assertTrue(u.isSeller());
    }

    @Test
    public void User() {
        User u = new User("Eszter", "12345", false);
        assertNotNull(u);
        User u2 = new User();
        assertNotNull(u2);

    }
}