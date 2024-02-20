package me.abdulkarim.carrental.selection;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import me.abdulkarim.carrental.vehicle.Vehicle;

public record ModifiableControls(Label outputLabel, ListView<Vehicle> listView, TextField textField, CheckBox checkBox1,
                                 CheckBox checkBox2, CheckBox checkBox3) {
}