package com.example.uscdoordrinkteam65;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Location {
    private String name;
    private double latitude;
    private double longitude;
    private List<Drink> menu;
    public Location(){

    }
    public Location(String name, double latitude, double longitude, List<Drink> menu) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        if (menu == null) {
            this.menu = new ArrayList<>();
        }
        else {this.menu = menu;}
    }
    public void addToMenu(Drink d) {
        if (menu == null) {
            this.menu = new ArrayList<>();
        } else {
            menu.add(d);
        }
    }
    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    public List<Drink> getMenu(){
        return menu;
    }

}