package me.abdulkarim.carrental.vehicle;

import me.abdulkarim.carrental.client.Client;

public class Vehicle {

    private final int modelYear;
    private final String make;
    private final String modelName;
    private final String licensePlate;

    private Client client;

    public Vehicle(int modelYear, String make, String modelName, String licensePlate) {
        this.modelYear = modelYear;
        this.make = make;
        this.modelName = modelName;
        this.licensePlate = licensePlate;
    }

    public int getModelYear() {
        return modelYear;
    }

    public String getMake() {
        return make;
    }

    public String getModelName() {
        return modelName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isAvailableForRent() {
        return client == null;
    }

    @Override
    public String toString() {
        return modelYear + ", " + make + ", " + modelName + ", " + licensePlate + ", "
                + (client == null ? "Available" : client.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vehicle vehicle))
            return false;

        return vehicle.getLicensePlate().equals(licensePlate);
    }
}