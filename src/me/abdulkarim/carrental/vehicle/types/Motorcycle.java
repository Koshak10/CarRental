package me.abdulkarim.carrental.vehicle.types;

import me.abdulkarim.carrental.vehicle.Vehicle;

public class Motorcycle extends Vehicle {

    private final boolean hasSidecar;

    public Motorcycle(int modelYear, String make, String modelName, String licensePlate, boolean hasSidecar) {
        super(modelYear, make, modelName, licensePlate);
        this.hasSidecar = hasSidecar;
    }

    public boolean hasSidecar() {
        return hasSidecar;
    }

    @Override
    public String toString() {
        return getModelYear() + ", "
                + getMake() + ", "
                + getModelName() + ", "
                + getLicensePlate() + ", Motorcycle, "
                + hasSidecar + ", "
                + (getClient() == null ? "Available" : getClient().getId());
    }

}