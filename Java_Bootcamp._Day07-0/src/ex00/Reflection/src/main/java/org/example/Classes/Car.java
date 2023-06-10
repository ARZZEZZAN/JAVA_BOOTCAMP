package org.example.Classes;

public class Car {
    private String model;
    private Double motor;
    private Double mileage;
    private int horsepower;

    public Car() {
        this.model = "Unknown";
        this.motor = 0.0;
        this.horsepower = 0;
    }

    public Car(String model, Double motor, Double mileage, int horsepower) {
        this.model = model;
        this.motor = motor;
        this.mileage = mileage;
        this.horsepower = horsepower;
    }

    public double growMileage(double value) {
        this.mileage += value;
        return mileage;
    }
    private void onEngine(String command) {
        System.out.println("Car " + model + " wound up");
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", motor=" + motor +
                ", mileage=" + mileage +
                ", horsepower=" + horsepower +
                '}';
    }
}
