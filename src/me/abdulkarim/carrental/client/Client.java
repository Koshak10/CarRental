package me.abdulkarim.carrental.client;

import me.abdulkarim.carrental.vehicle.Vehicle;

public class Client {

    private final int id;
    private final String name;
    private final String phoneNumber;

    private Vehicle vehicle;

    public Client(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isRentingVehicle() {
        return vehicle != null;
    }

    @Override
    public String toString() {
        return id + ", " + name + ", " + phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Client client))
            return false;

        return client.getId() == id;
    }

}