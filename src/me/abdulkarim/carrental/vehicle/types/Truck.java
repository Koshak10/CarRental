package me.abdulkarim.carrental.vehicle.types;

import me.abdulkarim.carrental.vehicle.Vehicle;

public class Truck extends Vehicle {

    private final int payloadCapacity;

    public Truck(int modelYear, String make, String modelName, String licensePlate, int payloadCapacity) {
        super(modelYear, make, modelName, licensePlate);
        this.payloadCapacity = payloadCapacity;
    }

    public int getPayloadCapacity() {
        return payloadCapacity;
    }

    @Override
    public String toString() {
        return getModelYear() + ", "
                + getMake() + ", "
                + getModelName() + ", "
                + getLicensePlate() + ", Truck, "
                + payloadCapacity + ", "
                + (getClient() == null ? "Available" : getClient().getId());
    }
}