package br.dayanelima.voudebike.data;

public class Bike {

    public static final int UNSET_ID = -1;

    public int id = UNSET_ID;
    public String name = "";
    public String description = "";
    public String type = "";
    public String color = "";
    // Price per day in "local currency" €, $, ...
    public int price = 0;

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }



}



