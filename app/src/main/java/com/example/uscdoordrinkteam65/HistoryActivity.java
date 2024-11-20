package com.example.uscdoordrinkteam65;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity{
    BarChart barChart, barChart2;
    //ArrayList<String> stores = new ArrayList<String>();
    Map<String, Integer> stores = new HashMap<String, Integer>();
    Map<String, Integer> orders = new HashMap<String, Integer>();
    int countPressed = 0;
    String userIDtemp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_customer);
        barChart = findViewById(R.id.barChart_stores);
        barChart2 = findViewById(R.id.barChart_drinks);

    }
    public void getOrderHistoryUser(View v) {
        //int userID = User.getUserID();
        /*NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify= new Notification.Builder
                (getApplicationContext()).setContentTitle("Recommendation").setContentText("Coffee from dining hall").
                setContentTitle("Recommendation").setSmallIcon(R.drawable.ic_launcher_background).build();
        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);*/
        switch (v.getId()){
            case R.id.day:
                getData(1);
                initBarChart1();
                showBarChart1();
                initBarChart2();
                showBarChart2();
                break;
            case R.id.week:
                getData(2);
                initBarChart1();
                showBarChart1();
                initBarChart2();
                showBarChart2();
                break;
            case R.id.month:
                getData(3);
                initBarChart1();
                showBarChart1();
                initBarChart2();
                showBarChart2();
                break;
        }
        //get most common drink
        int countTmp = 0;
        String orderTemp = "";
        for (Map.Entry<String,Integer> entry : orders.entrySet())
        {
            if(entry.getValue() > countTmp) {
                orderTemp = entry.getKey();
                countTmp = entry.getValue();

            }
        }
        countTmp = 0;
        //get most common store
        String storeTemp = "";
        for (Map.Entry<String,Integer> entry : stores.entrySet())
        {
            if(entry.getValue() > countTmp) {
                storeTemp = entry.getKey();
                countTmp = entry.getValue();
            }
        }
        if(countTmp != 0) {
            String rec = "Recommendation: Drink " + orderTemp + " and go to " + storeTemp;
            Snackbar.make(v, rec, 2000).show();
        }
        stores.clear();
        orders.clear();
    }

    public void getData(int num){
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://usc-doordrink-default-rtdb.firebaseio.com").getReference("Deliveries");
        //DatabaseReference historyRef = userRef.child("Test");
        userRef.addValueEventListener(new ValueEventListener()  {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.now();
                String currDate = dtf.format(localDate);
                LocalDate to = LocalDate.parse(currDate);
                DataSnapshot user = snapshot.child(GlobalVars.currCustomer.getUserID());
                //for (DataSnapshot user : snapshot.getChildren()) {
                //Log.d("tag", user.child("name").getValue().toString().trim());
                if (user != null) {
                    //if (user.child("name").getValue().toString().trim().equals("Test")) {
                    //if(userIDtemp == GlobalVars.currCustomer.getUserID())
                    //Log.d("tag", GlobalVars.currCustomer.getName());
                    //Log.d("tag", GlobalVars.currCustomer.getUserID());
                    //DataSnapshot count = user.child("currentOrder");
                    for (DataSnapshot count : user.getChildren()) {
                        String storeTemp = count.child("storeName").getValue().toString();
                        //Log.d("tag", historyInfo.child("date").getValue().toString());
                        String orderTemp = "";
                        LocalDate from = LocalDate.parse(count.child("date").getValue().toString());
                        boolean withinTimeFrame = false;
                        //Log.d("tag", String.valueOf(num));
                        if (num == 1) {
                            long days = ChronoUnit.DAYS.between(from, to);
                            if (days <= 1) {
                                withinTimeFrame = true;
                            }
                            //Log.d("tag", String.valueOf(days));
                        }
                        if (num == 2) {
                            long months = ChronoUnit.MONTHS.between(from, to);
                            if (months <= 1) {
                                withinTimeFrame = true;
                            }
                            //Log.d("tag", String.valueOf(months));
                        }
                        if (num == 3) {
                            long years = ChronoUnit.YEARS.between(from, to);
                            if (years <= 1) {
                                withinTimeFrame = true;
                            }
                            //Log.d("tag", String.valueOf(years));
                        }
                        if (withinTimeFrame) {
                           if (stores.containsKey(storeTemp)) {
                                stores.put(storeTemp, stores.get(storeTemp) + 1);
                            } else {
                                stores.put(storeTemp, 1);
                            }
                            DataSnapshot orderDetails = count.child("order");
                            for (DataSnapshot drinks : orderDetails.child("drinks").getChildren()) {
                                orderTemp = drinks.child("name").getValue().toString();
                                int countOrder = Integer.parseInt(drinks.child("count").getValue().toString());
                                if (orders.containsKey(orderTemp)) {
                                    orders.put(orderTemp, orders.get(orderTemp) + countOrder);
                                } else {
                                    orders.put(orderTemp, countOrder);
                                }
                            }
                        }
                    }
                    //Log.d("tag", String.valueOf(stores.get(storeTemp)));
                    //Log.d("tag", String.valueOf(orders.get(orderTemp)));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initBarChart1(){
        //hiding the grey background of the chart, default false if not set
        barChart.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart.animateX(1000);

        XAxis xAxis = barChart.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barChart.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

    }
    private void initBarChart2(){
        //hiding the grey background of the chart, default false if not set
        barChart2.setDrawGridBackground(false);
        //remove the bar shadow, default false if not set
        barChart2.setDrawBarShadow(false);
        //remove border of the chart, default false if not set
        barChart2.setDrawBorders(false);

        //remove the description label text located at the lower right corner
        Description description = new Description();
        description.setEnabled(false);
        barChart2.setDescription(description);

        //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
        barChart2.animateY(1000);
        //setting animation for x-axis, the bar will pop up separately within the time we set
        barChart2.animateX(1000);

        XAxis xAxis = barChart2.getXAxis();
        //change the position of x-axis to the bottom
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //set the horizontal distance of the grid line
        xAxis.setGranularity(1f);
        //hiding the x-axis line, default true if not set
        xAxis.setDrawAxisLine(false);
        //hiding the vertical grid lines, default true if not set
        xAxis.setDrawGridLines(false);

        YAxis leftAxis = barChart2.getAxisLeft();
        //hiding the left y-axis line, default true if not set
        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = barChart2.getAxisRight();
        //hiding the right y-axis line, default true if not set
        rightAxis.setDrawAxisLine(false);

        Legend legend = barChart2.getLegend();
        //setting the shape of the legend form to line, default square shape
        legend.setForm(Legend.LegendForm.LINE);
        //setting the text size of the legend
        legend.setTextSize(11f);
        //setting the alignment of legend toward the chart
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        //setting the stacking direction of legend
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //setting the location of legend outside the chart, default false if not set
        legend.setDrawInside(false);

    }
    public void initBarDataSet(BarDataSet barDataSet){
        //Changing the color of the bar
        barDataSet.setColor(Color.parseColor("#304567"));
        //Setting the size of the form in the legend
        barDataSet.setFormSize(15f);
        //showing the value of the bar, default true if not set
        barDataSet.setDrawValues(false);
        //setting the text size of the value of the bar
        barDataSet.setValueTextSize(12f);
    }
    public void showBarChart1(){
        //ArrayList<Double> valueList = new ArrayList<Double>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Stores You've Visited";

        //input data
       // for(int i = 0; i < 6; i++){
            //valueList.add(i * 50.1);
        //}

        //fit the data into a bar
        int count = 0;
        for (Map.Entry<String,Integer> entry : stores.entrySet())
        {
            BarEntry barEntry = new BarEntry(count,entry.getValue());
            entries.add(barEntry);
            count++;
        }
        BarDataSet barDataSet = new BarDataSet(entries, title);
        BarData data = new BarData(barDataSet);
        barChart.setData(data);
        barChart.invalidate();
        ArrayList<String> labels = new ArrayList<>();
        //set labels of chart
        for (Map.Entry<String,Integer> entry : stores.entrySet())
        {
            labels.add(entry.getKey());
        }
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        initBarDataSet(barDataSet);
    }
    public void showBarChart2(){
       ArrayList<BarEntry> entries = new ArrayList<>();
        String title = "Drinks You've Had";
        int count = 0;
        for (Map.Entry<String,Integer> entry : orders.entrySet())
        {
            BarEntry barEntry = new BarEntry(count,entry.getValue());
            entries.add(barEntry);
            count++;
        }
        BarDataSet barDataSet = new BarDataSet(entries, title);
        BarData data = new BarData(barDataSet);
        barChart2.setData(data);
        barChart2.invalidate();
        ArrayList<String> labels = new ArrayList<>();
        //set labels of chart
        for (Map.Entry<String,Integer> entry : orders.entrySet())
        {
            labels.add(entry.getKey());
        }
        barChart2.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        initBarDataSet(barDataSet);
    }

}
