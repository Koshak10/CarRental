package me.abdulkarim.carrental.selection;

import me.abdulkarim.carrental.client.Client;
import me.abdulkarim.carrental.vehicle.Vehicle;

public class Selection {

    private Client client;
    private Vehicle vehicle;

    private final ModifiableControls modifiableControls;

    public Selection(Client client, Vehicle vehicle, ModifiableControls modifiableControls) {
        this.client = client;
        this.vehicle = vehicle;
        this.modifiableControls = modifiableControls;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ModifiableControls getModifiableControls() {
        return modifiableControls;
    }

    public void clear() {
        this.client = null;
        this.vehicle = null;
    }

}