package me.abdulkarim.carrental.vehicle.types;

import me.abdulkarim.carrental.vehicle.Vehicle;

public class Car extends Vehicle {

    private final boolean hybrid;

    public Car(int modelYear, String make, String modelName, String licensePlate, boolean hybrid) {
        super(modelYear, make, modelName, licensePlate);
        this.hybrid = hybrid;
    }

    public boolean isHybrid() {
        return hybrid;
    }

    @Override
    public String toString() {
        return getModelYear() + ", "
                + getMake() + ", "
                + getModelName() + ", "
                + getLicensePlate() + ", Car, "
                + hybrid + ", "
                + (getClient() == null ? "Available" : getClient().getId());
    }
}