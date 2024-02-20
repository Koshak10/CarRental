package me.abdulkarim.carrental.windows.vehicle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.abdulkarim.carrental.client.Client;
import me.abdulkarim.carrental.client.ClientManager;
import me.abdulkarim.carrental.selection.ModifiableControls;
import me.abdulkarim.carrental.selection.Selection;
import me.abdulkarim.carrental.selection.SelectionManager;
import me.abdulkarim.carrental.vehicle.Vehicle;
import me.abdulkarim.carrental.vehicle.VehicleManager;
import me.abdulkarim.carrental.vehicle.types.Car;
import me.abdulkarim.carrental.vehicle.types.Motorcycle;
import me.abdulkarim.carrental.vehicle.types.Truck;
import me.abdulkarim.carrental.windows.WindowOpener;

import java.util.ArrayList;

public class VehicleWindow {

    @FXML
    private Button backButton;

    @FXML
    private Label brandLabel;

    @FXML
    private TextField brandTextField;

    @FXML
    private CheckBox check1;

    @FXML
    private CheckBox check2;

    @FXML
    private CheckBox check3;

    @FXML
    private Button deleteButton;

    @FXML
    private Label licensePlateLabel;

    @FXML
    private TextField licensePlateTextField;

    @FXML
    private Label modelNameLabel;

    @FXML
    private TextField modelNameTextField;

    @FXML
    private Label modelYearLabel;

    @FXML
    private TextField modelYearTextField;

    @FXML
    private Label outputLabel;

    @FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private Button receiveButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label registeredVehiclesLabel;

    @FXML
    private Button rentButton;

    @FXML
    private Label rentInfoLabel;

    @FXML
    private TextField rentTextField;

    @FXML
    private Label rentToLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Label typeLabel;

    @FXML
    private Label typeSpecialLabel;

    @FXML
    private TextField typeSpecialTextField;

    @FXML
    private ListView<Vehicle> vehicleList;

    public void initialize() {
        ClientManager.getInstance().setup();
        VehicleManager.getInstance().setup();

        ArrayList<Vehicle> vehicles = VehicleManager.getInstance().getVehicles();
        for (Vehicle vehicle : vehicles) {
            vehicleList.getItems().add(vehicle);
        }

        registeredVehiclesLabel.setText("Registered Vehicles: (" + vehicles.size() + ")");

        typeComboBox.getItems().add("Car");
        typeComboBox.getItems().add("Truck");
        typeComboBox.getItems().add("Motorcycle");

        ToggleGroup toggleGroup = new ToggleGroup();
        radio1.setToggleGroup(toggleGroup);
        radio2.setToggleGroup(toggleGroup);
    }

    @FXML
    private void itemStateChanged(ActionEvent event) {
        vehicleList.getItems().clear();

        boolean isCarCheckBoxSelected = check1.isSelected();
        boolean isTruckCheckBoxSelected = check2.isSelected();
        boolean isMotorcycleCheckBoxSelected = check3.isSelected();

        for (Vehicle vehicle : VehicleManager.getInstance().getVehicles()) {
            if (isCarCheckBoxSelected && vehicle instanceof Car) {
                vehicleList.getItems().add(vehicle);
            }

            if (isTruckCheckBoxSelected && vehicle instanceof Truck) {
                vehicleList.getItems().add(vehicle);
            }

            if (isMotorcycleCheckBoxSelected && vehicle instanceof Motorcycle) {
                vehicleList.getItems().add(vehicle);
            }
        }
    }

    @FXML
    private void valueChanged() {
        Vehicle vehicle = vehicleList.getSelectionModel().getSelectedItem();
        brandTextField.setText(vehicle.getMake());
        modelNameTextField.setText(vehicle.getModelName());
        modelYearTextField.setText(String.valueOf(vehicle.getModelYear()));
        licensePlateTextField.setText(vehicle.getLicensePlate());
        rentTextField.setText("Available");

        if (!vehicle.isAvailableForRent()) {
            Client client = vehicle.getClient();
            rentTextField.setText(client.getId() + " - " + client.getName());
        }

        if (vehicle instanceof Car car) {
            typeComboBox.getSelectionModel().select(0);

            typeSpecialLabel.setVisible(true);
            typeSpecialLabel.setText("Hybrid: ");

            typeSpecialTextField.setVisible(false);

            radio1.setVisible(true);
            radio2.setVisible(true);

            if (car.isHybrid())
                radio1.setSelected(true);
            else
                radio2.setSelected(true);
        } else if (vehicle instanceof Truck truck) {
            typeComboBox.getSelectionModel().select(1);

            typeSpecialLabel.setVisible(true);
            typeSpecialLabel.setText("Payload Capacity: ");

            typeSpecialTextField.setVisible(true);
            typeSpecialTextField.setText("" + truck.getPayloadCapacity());
        } else if (vehicle instanceof Motorcycle motorcycle) {
            typeComboBox.getSelectionModel().select(2);

            typeSpecialLabel.setVisible(true);
            typeSpecialLabel.setText("Has Sidecar: ");

            typeSpecialTextField.setVisible(false);

            radio1.setVisible(true);
            radio2.setVisible(true);

            if (motorcycle.hasSidecar())
                radio1.setSelected(true);
            else
                radio2.setSelected(true);
        }
    }

    @FXML
    private void actionPerformed(ActionEvent event) {
        if (event.getSource() == backButton) {
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();

            WindowOpener.showMainWindow();
            return;
        }

        if (event.getSource() == rentButton) {
            boolean isNullSelection = vehicleList.getSelectionModel().isEmpty();
            if (isNullSelection) {
                outputLabel.setText("ERROR - You must select a vehicle from the list!");
                outputLabel.setTextFill(Color.RED);
                return;
            }

            Vehicle vehicle = vehicleList.getSelectionModel().getSelectedItem();

            ModifiableControls modifiableControls = new ModifiableControls(outputLabel, vehicleList, rentTextField, check1, check2, check3);

            Selection selection = new Selection(null, vehicle, modifiableControls);
            SelectionManager.getInstance().setSelection(selection);

            WindowOpener.showSelectionWindow();
            return;
        }

        if (event.getSource() == receiveButton) {
            Vehicle vehicle = vehicleList.getSelectionModel().getSelectedItem();

            if (VehicleManager.getInstance().receiveVehicle(vehicle, outputLabel)) {
                refreshList();
                rentTextField.setText("Available");
            }
        }

        if (!areFieldsValid()) {
            outputLabel.setText("ERROR - Missing information!");
            outputLabel.setTextFill(Color.RED);
            return;
        }

        String brand = brandTextField.getText();
        String modelName = modelNameTextField.getText();
        int modelYear = Integer.parseInt(modelYearTextField.getText());
        String licensePlate = licensePlateTextField.getText();

        Vehicle vehicle = null;

        String type = typeComboBox.getSelectionModel().getSelectedItem();
        switch (type) {
            case "Car" -> {
                boolean hybrid = radio1.isSelected();
                vehicle = new Car(modelYear, brand, modelName, licensePlate, hybrid);
            }
            case "Truck" -> {
                int payloadCapacity = Integer.parseInt(typeSpecialTextField.getText());
                vehicle = new Truck(modelYear, brand, modelName, licensePlate, payloadCapacity);
            }
            case "Motorcycle" -> {
                boolean hasSidecar = radio1.isSelected();
                vehicle = new Motorcycle(modelYear, brand, modelName, licensePlate, hasSidecar);
            }
        }

        if (event.getSource() == registerButton) {
            if (VehicleManager.getInstance().newRecord(vehicle, outputLabel)) {
                vehicleList.getItems().add(vehicle);
                clearTextFields();
            }
        } else if (event.getSource() == deleteButton) {
            if (VehicleManager.getInstance().deleteRecord(vehicle, outputLabel)) {
                vehicleList.getItems().remove(vehicle);
                clearTextFields();
            }
        }
    }

    @FXML
    private void changeComboBox() {
        int index = typeComboBox.getSelectionModel().getSelectedIndex();
        if (index == 0) {
            typeSpecialLabel.setVisible(true);
            typeSpecialLabel.setText("Hybrid: ");

            typeSpecialTextField.setVisible(false);

            radio1.setVisible(true);
            radio2.setVisible(true);
            radio2.setSelected(true);
        } else if (index == 1) {
            typeSpecialLabel.setVisible(true);
            typeSpecialLabel.setText("Payload Capacity: ");

            typeSpecialTextField.setVisible(true);
            typeSpecialTextField.setText("");
        } else {
            typeSpecialLabel.setVisible(true);
            typeSpecialLabel.setText("Has Sidecar: ");

            typeSpecialTextField.setVisible(false);

            radio1.setVisible(true);
            radio2.setVisible(true);
            radio2.setSelected(true);
        }
    }

    private void refreshList() {
        this.vehicleList.getItems().clear();
        for (Vehicle vehicle : VehicleManager.getInstance().getVehicles()) {
            vehicleList.getItems().add(vehicle);
        }
    }

    private boolean areFieldsValid() {
        return !brandTextField.getText().isEmpty()
                && !modelNameTextField.getText().isEmpty()
                && !modelYearTextField.getText().isEmpty()
                && !licensePlateTextField.getText().isEmpty()
                && typeComboBox.getSelectionModel().getSelectedItem() != null;
    }

    private void clearTextFields() {
        brandTextField.setText("");
        modelNameTextField.setText("");
        modelYearTextField.setText("");
        licensePlateTextField.setText("");
        typeComboBox.getSelectionModel().clearSelection();

        if (typeSpecialLabel.isVisible()) {
            typeSpecialLabel.setVisible(false);
        }

        if (typeSpecialTextField.isVisible()) {
            typeSpecialTextField.setVisible(false);
        }

        if (radio1.isVisible()) {
            radio1.setVisible(false);
        }

        if (radio2.isVisible()) {
            radio2.setVisible(false);
        }
    }

}
