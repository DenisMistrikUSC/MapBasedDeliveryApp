package com.example.uscdoordrinkteam65;

import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Customer extends User {
    private int totalCaffeineIntake;
    private Order currentOrder;
    private ArrayList<Order> history;
//    public Customer(String name, String userID) {
//        super(name, userID, false);
//        totalCaffeineIntake = 0;
//        currentOrder = new Order();
//        history = new ArrayList<>();
//
//    }
    public Customer() {
        super();

    }
    public Customer(String name, String userID, Order currentOrder, ArrayList<Order> history) {
        super(name, userID, false);
        if (currentOrder == null) {
            this.currentOrder = new Order();
        }
        else {
            this.currentOrder = currentOrder;
        }

        totalCaffeineIntake = this.currentOrder.getCaffeineTotal();
        if (history == null) {
            this.history = new ArrayList<>();
        }
        else {
            this.history = history; }
        updateCaffeineIntake();
    }

//    public void resetOrder() {
//        currentOrder.resetOrder();
//    }
    public void checkOutCurrentOrder() {
        // add a copy of the current order to history
        Order temp = new Order();
        for (Drink d: currentOrder.getDrinks()) {
            Drink tempDrink = new Drink(d.getCaffeine(), d.getName(), d.getPrice());
            temp.addToOrder(tempDrink);
        }
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(temp);
        currentOrder.resetOrder();
    }
    public void addToOrder(Drink d) {
        if (currentOrder == null) {
            currentOrder = new Order();
        }
        currentOrder.addToOrder(d);
        totalCaffeineIntake += d.getCaffeine();
    }
    // get all the caffeine from orders placed today
    public void updateCaffeineIntake() {
        String today = LocalDate.now().toString();
        if (currentOrder == null) {
            currentOrder = new Order();
        }
        totalCaffeineIntake = currentOrder.getCaffeineTotal();
        if (history != null) {
            for (Order o : history) {
                if (o.getDate().equals(today)) {
                    totalCaffeineIntake += o.getCaffeineTotal();
                }
            }
        }
    }
    public Order getCurrentOrder() {
        return currentOrder;
    }

    public boolean checkIfOverLimit() {
        if (totalCaffeineIntake >= 400) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addToCaffeine(int amount) {
        totalCaffeineIntake += amount;
    }
    public void resetCaffeineIntake() {
        totalCaffeineIntake = 0;
    }
    public boolean checkCanHaveDrink(Drink d) {
        if (totalCaffeineIntake + d.getCaffeine() > 400) {
            return false;
        }
        else {
            return true;
        }
    }
    public int getTotalCaffeineIntake() {
        return totalCaffeineIntake;
    }

    public int getNumOrders() {
        if (history == null) {
            return 0;
        }
        return history.size();
    }
    public ArrayList<Order> getHistory(){ return history;}
}
