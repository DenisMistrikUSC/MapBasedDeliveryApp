package com.example.uscdoordrinkteam65;

import java.time.LocalDateTime;

public class Delivery {
    private Order order;
    private String startTime;
    private String endTime;
    private String date;
    private String storeName;
    private int duration;
    public Delivery(Order o, int mDuration, String storeName) {
        order = o;
//        LocalDate dateObj = LocalDate.parse(date);
//        LocalTime startTimeObj = LocalTime.now();
        duration = mDuration;
        LocalDateTime startTimeObj = LocalDateTime.now();
        LocalDateTime endTimeObj = startTimeObj.plusMinutes((long) mDuration);
        date = startTimeObj.toLocalDate().toString();
        startTime = startTimeObj.toString();
        endTime = endTimeObj.toString();
        this.storeName = storeName;
    }

    public int getDuration() {
        return duration;
    }
    public String getStartTime() {
        return startTime;
    }
    public Order getOrder() {
        return order;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getDate() {
        return date;
    }
    public String getStoreName() {
        return storeName;
    }

}
