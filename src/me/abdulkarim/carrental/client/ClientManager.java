package me.abdulkarim.carrental.client;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import me.abdulkarim.carrental.vehicle.Vehicle;
import me.abdulkarim.carrental.vehicle.VehicleManager;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientManager {

    private ClientManager() { }

    private static final ClientManager INSTANCE = new ClientManager();

    public static ClientManager getInstance() {
        return INSTANCE;
    }

    private final ArrayList<Client> clients = new ArrayList<>();

    public void setup() {
        this.clients.clear();

        try {
            File file = new File("Clients.txt");
            Scanner inputFile = new Scanner(file);

            while (inputFile.hasNext()) {
                String str = inputFile.nextLine();
                String[] items = str.split(", ");

                int id = Integer.parseInt(items[0]);
                String name = items[1];
                String phoneNumber = items[2];

                Client client = new Client(id, name, phoneNumber);
                clients.add(client);
            }

            inputFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public boolean newRecord(Client client, Label outputLabel) {
        if (clients.contains(client)) {
            outputLabel.setText("ERROR - Client already exists!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        try {
            FileWriter fileWriter = new FileWriter("Clients.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(client);
            printWriter.close();

            this.clients.add(client);

            outputLabel.setText("SUCCESS - Client successfully registered.");
            outputLabel.setTextFill(Color.GREEN);
            return true;
        } catch (IOException e) {
            outputLabel.setText("ERROR - File not found.");
            outputLabel.setTextFill(Color.RED);
            return false;
        }
    }

    public boolean deleteRecord(Client client, Label outputLabel) {
        if (!clients.contains(client)) {
            outputLabel.setText("ERROR - Client does not exist in the database!");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        if (client.isRentingVehicle()) {
            outputLabel.setText("ERROR - This client is currently renting a vehicle! Receive the vehicle back first.");
            outputLabel.setTextFill(Color.RED);
            return false;
        }

        this.clients.remove(client);
        this.textFile();

        outputLabel.setText("SUCCESS - Client was removed from the database!");
        outputLabel.setTextFill(Color.GREEN);
        return true;
    }

    public void textFile() {
        try {
            PrintWriter outputFile = new PrintWriter("Clients.txt");

            for (Client client : clients) {
                outputFile.println(client);
            }

            outputFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Client getClient(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return client;
            }
        }

        return null;
    }

    public void loadVehicles() {
        for (Vehicle vehicle : VehicleManager.getInstance().getVehicles()) {
            for (Client client : clients) {
                if (!vehicle.isAvailableForRent() && vehicle.getClient().equals(client)) {
                    client.setVehicle(vehicle);
                }
            }
        }
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

}