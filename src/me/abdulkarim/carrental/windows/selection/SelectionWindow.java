package me.abdulkarim.carrental.windows.selection;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import me.abdulkarim.carrental.client.Client;
import me.abdulkarim.carrental.client.ClientManager;
import me.abdulkarim.carrental.selection.ModifiableControls;
import me.abdulkarim.carrental.selection.Selection;
import me.abdulkarim.carrental.selection.SelectionManager;
import me.abdulkarim.carrental.vehicle.Vehicle;
import me.abdulkarim.carrental.vehicle.VehicleManager;

public class SelectionWindow {

    @FXML
    private Button cancelButton;

    @FXML
    private Label label;

    @FXML
    private ListView<Client> listView;

    @FXML
    private Button rentButton;

    @FXML
    private TextField textField;

    public void initialize() {
        ClientManager.getInstance().setup();
        ClientManager.getInstance().loadVehicles();

        for (Client client : ClientManager.getInstance().getClients()) {
            if (!client.isRentingVehicle()) listView.getItems().add(client);
        }
    }

    @FXML
    private void valueChanged() {
        Client client = listView.getSelectionModel().getSelectedItem();
        textField.setText(client.getId() + " - " + client.getName());
    }

    @FXML
    private void actionPerformed(ActionEvent event) {
        if (event.getSource() == rentButton) {
            closeWindow();

            Client client = listView.getSelectionModel().getSelectedItem();
            Selection selection = SelectionManager.getInstance().getSelection();
            selection.setClient(client);

            ModifiableControls modifiableControls = selection.getModifiableControls();

            if (VehicleManager.getInstance().rentVehicle(selection.getVehicle(), selection.getClient(), modifiableControls.outputLabel())) {
                modifiableControls.listView().getItems().clear();

                for (Vehicle vehicle : VehicleManager.getInstance().getVehicles()) {
                    modifiableControls.listView().getItems().add(vehicle);
                }

                modifiableControls.textField().setText(selection.getClient().getId() + " - " + selection.getClient().getName());

                modifiableControls.checkBox1().setSelected(false);
                modifiableControls.checkBox2().setSelected(false);
                modifiableControls.checkBox3().setSelected(false);
            }

            selection.clear();
        } else if (event.getSource() == cancelButton) {
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}