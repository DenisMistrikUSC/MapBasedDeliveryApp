package com.example.uscdoordrinkteam65;

import java.time.LocalDate;
import java.util.ArrayList;


public class Order {
    private ArrayList<Drink> drinks;
//    private Customer c;
    private double totalCost = 0;
    private int caffeineTotal = 0;
    private String d;
    public Order() {
//        this.c = c;
        drinks = new ArrayList<>();
        d = LocalDate.now().toString();
    }
    public void addToOrder(Drink d){
        drinks.add(d);
        totalCost += d.getPrice();
        caffeineTotal += d.getCaffeine();
    }
    public void resetOrder() {
        drinks = new ArrayList<>();
    }
    public ArrayList<Drink> getDrinks() {
        return drinks;
    }
//    public Customer getCustomer() {
//        return c;
//    }

    public String getDate() {
        return d;
    }
    public void addSupplementalInfo(double cost, int caffeine) {
        totalCost += cost;
        caffeineTotal += caffeine;
    }
    public double getTotalCost() {
        return totalCost;
    }

    public int getCaffeineTotal() {
        return caffeineTotal;
    }
}
