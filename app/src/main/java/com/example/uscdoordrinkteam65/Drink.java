package com.example.uscdoordrinkteam65;

public class Drink {
    private String name;
    private int caffeine;
    private double price;
    private double discountPrice;
    private int count;
    public Drink(){
        count = 1;
    }
    public Drink(int caffeine, String name, double price) {
        this.name = name;
        this.caffeine = caffeine;
        // add a rounding method if price has more decimal places
        this.price = price;
        count = 1;
    }
//    public void setDiscountPrice(double dPrice) {
//        if (dPrice < price ) {
//            discountPrice = dPrice;
//        }
//    }
//    public void removeDiscount() {
//        discountPrice = price;
//    }
    public String getName() {
        return name;
    }
    public int getCaffeine() {
        return caffeine;
    }
    public double getPrice() {
        return price;
    }
    public void setName(String newName) {
        name = newName;
    }
    public void setCaffeine(int newVal) {
        caffeine = newVal;
    }
    public void setPrice(double newPrice) {
        price = newPrice;
    }
    public void addCount(){
        count++;
    }
    public int getCount(){
        return count;
    }
    //public boolean isDiscounted() {
     //   return discountPrice != price;
   // }

}