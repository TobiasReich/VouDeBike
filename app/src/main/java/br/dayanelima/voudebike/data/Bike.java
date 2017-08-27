package br.dayanelima.voudebike.data;

public class Bike {

    public int id = 1;
    public String name = "My bike";
    public String description = "This is my bike";
    public String type = "A bike for kids";
    public String color = "Red";
    // Price per day in "local currency" €, $, ...
    public int price = 100;

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}



