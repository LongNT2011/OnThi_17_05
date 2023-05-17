package com.example.onthi_17_05;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Bill implements Comparable<Bill>, Serializable {
    @Nullable
    private int id;
    private String number;
    private double distance;
    private double price;
    private int percent;

    public Bill(int id, String number, double distance, double price, int percent) {
        this.id = id;
        this.number = number;
        this.distance = distance;
        this.price = price;
        this.percent = percent;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public double getSum(){
        return price * distance * (100 - percent) / 100;
    }
    @Override
    public int compareTo(Bill o) {
        return this.number.compareTo(o.getNumber());
    }
}
