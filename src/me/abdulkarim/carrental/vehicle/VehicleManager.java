package me.abdulkarim.carrental.vehicle;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import me.abdulkarim.carrental.client.Client;
import me.abdulkarim.carrental.client.ClientManager;
import me.abdulkarim.carrental.vehicle.types.Car;
import me.abdulkarim.carrental.vehicle.types.Motorcycle;
import me.abdulkarim.carrental.vehicle.types.Truck;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class VehicleManager {

    private VehicleManager() { }

    private static final VehicleManager INSTANCE = new VehicleManager();

    public static VehicleManager getInstance() {
        return INSTANCE;
    }

    private final ArrayList<Vehicle> vehicles = new ArrayList<>();

    public void setup() {
        this.vehicles.clear();

        // Reading Data from a File
        try {
            File file = new File("Vehicles.txt");
            Scanner inputFile = new Scanner(file);

            while (inputFile.hasNext()) {
                String str = inputFile.nextLine();
                String[] items = str.split(", ");

                int modelYear = Integer.parseInt(items[0]);
                String make = items[1];
                String modelName = items[2];
                String licensePlate = items[3];
                String type = items[4];
                String lastItem = items[6];

                Vehicle vehicle = null;
                switch (type) {
                    case "Car" -> {
                        boolean hybrid = Boolean.parseBoolean(items[5]);
                        vehicle = new Car(modelYear, make, modelName, licensePlate, hybrid);
                    }
                    case "Truck" -> {
                        int payloadCapacity = Integer.parseInt(items[5]);
                        vehicle = new Truck(modelYear, make, modelName, licensePlate, payloadCapacity);
                    }
                    case "Motorcycle" -> {
                        boolean hasSidecar = Boolean.parseBoolean(items[5]);
                        vehicle = new Motorcycle(modelYear, make, modelName, licensePlate, hasSidecar);
                    }
                }

                if (vehicle == null) {
                    return;
                }

                if (!lastItem.equals("Available")) {
                    int id = Integer.parseInt(lastItem);

                    Client client = ClientManager.getInstance().getClient(id);
                    client.setVehicle(vehicle);

                    vehicle.setClient(client);
                }

                vehicles.add(vehicle);
            }

            inputFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void textFile() {
        try {
            PrintWriter outputFile = new PrintWriter("Vehicles.txt");

            for (Vehicle vehicle : vehicles) {
                outputFile.println(vehicle);
            }

            outputFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public boolean newRecord(Vehicle newVehicle, Label outputLabel) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getLicensePlate().equals(newVehicle.getLicensePlate())) {
                outputLabel.setText("ERROR - Vehicle already exists in the database!");
                outputLabel.setTextFill(Color.RED);
                return false;
            }

            if (vehicle.getModelYear() == newVehicle.getModelYear()
                    && vehicle.getMake().equals(newVehicle.getMake())
                    && vehicle.getModelName().equals(newVehicle.getModelName())
                    && vehicle.getLicensePlate().equals(newVehicle.getLicensePlate())) {
                outputLabel.setText("ERROR - Vehicle already exists in the database!");
                outputLabel.setTextFill(Color.RED);
                return false;
            }
        }

        try {
            FileWriter fileWriter = new FileWriter("Vehicles.txt", true);
            PrintWriter outputFile = new PrintWriter(fileWriter);
            outputFile.println(newVehicle);
            outputFile.close();

            this.vehicles.add(newVehicle);

            outputLabel.setText("SUCCESS - Vehicle was added to the database!");
            outputLabel.setTextFill(Color.GREEN);

            return true;
        } catch (IOException e) {
            outputLabel.setText("ERROR - Could not write data to the file.");
            outputLabel.setTextFill(Color.RED);
            return false;
        }
    }

    public boolean deleteRecord(Vehicle vehicle, Label outputLabel) {
        if (!vehicles.contains(vehicle)) {
            outputLabel.setText("ERROR - Vehicle does not exist in the database!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        if (!vehicle.isAvailableForRent()) {
            outputLabel.setText("ERROR - This vehicle is currently rented! Make sure to receive it back first.");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        this.vehicles.remove(vehicle);
        this.textFile();

        outputLabel.setText("SUCCESS - Vehicle was removed from the database!");
        outputLabel.setTextFill(Color.GREEN);
        return true;
    }

    public boolean rentVehicle(Vehicle vehicle, Client client, Label outputLabel) {
        if (client == null) {
            outputLabel.setText("ERROR - Client does not exist in the database!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        if (client.isRentingVehicle()) {
            outputLabel.setText("ERROR - Client already renting a vehicle!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        if (vehicle == null) {
            outputLabel.setText("ERROR - Vehicle does not exist in the database!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        if (!vehicle.isAvailableForRent()) {
            outputLabel.setText("ERROR - This vehicle is already rented to a client!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        vehicle.setClient(client);
        textFile();

        outputLabel.setText("SUCCESS - Vehicle rented to: " + client.getId() + ", " + client.getName());
        outputLabel.setTextFill(Color.GREEN);
        return  true;
    }

    public boolean receiveVehicle(Vehicle vehicle, Label outputLabel) {
        if (vehicle == null) {
            outputLabel.setText("ERROR - Vehicle does not exist in the database!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        if (vehicle.isAvailableForRent()) {
            outputLabel.setText("ERROR - This vehicle is not rented to any client!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        vehicle.getClient().setVehicle(null);
        vehicle.setClient(null);
        this.textFile();

        outputLabel.setText("SUCCESS - Vehicle received back from the client!");
        outputLabel.setTextFill(Color.GREEN);
        return true;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

}