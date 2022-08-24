package ru.rtrs.rtrs_crack;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class Device {
    private TypeDevice typeDevice;
    private String power;
    private String ip;
    private String port;
    private boolean editable;
    private AnchorPane anchorPane;
    private TextField pwrDevice;
    private TextField ipDevice;
    private TextField portDevice;
    private JFXToggleButton toggleButton;
    private ComboBox<TypeDevice> comboBox;
    private ToggleGroup toggleGroup;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public Device(int id, ToggleGroup toggleGroup) {
        anchorPane = addDevice(id);
        this.toggleGroup = toggleGroup;
    }

    public Device(TypeDevice typeDevice, String power, String ip, String port, boolean editable) {
        this.typeDevice = typeDevice;
        this.power = power;
        this.ip = ip;
        this.port = port;
        this.editable = editable;
    }

    public TypeDevice getTypeDevice() {
        return typeDevice;
    }

    public String getPower() {
        return power;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setSettings() {
        power = getPwrDevice().getText();
        ip = getIpDevice().getText();
        port = getPortDevice().getText();
        typeDevice = getTypeDevice();

    }

    public TextField getPwrDevice() {
        return pwrDevice;
    }

    public TextField getIpDevice() {
        return ipDevice;
    }

    public TextField getPortDevice() {
        return portDevice;
    }

    public JFXToggleButton getToggleButton() {
        return toggleButton;
    }

    public ComboBox<TypeDevice> getComboBox() {
        return comboBox;
    }

    public AnchorPane addDevice(Integer id) {

        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(51.0);
        anchorPane.setPrefWidth(634.0);
        anchorPane.setId("pane" + id);
        anchorPane.setStyle("");

        pwrDevice = new TextField();
        pwrDevice.setLayoutX(308.0);
        pwrDevice.setLayoutY(14.0);
        pwrDevice.setPrefHeight(24.0);
        pwrDevice.setPrefWidth(69.0);
        pwrDevice.setId("pwrDevice" + id);
        pwrDevice.setText("100");
        pwrDevice.setStyle("-fx-text-inner-color: black;");
        pwrDevice.setStyle("-fx-background-color: #eda678;");


        ipDevice = new TextField();
        ipDevice.setLayoutX(395.0);
        ipDevice.setLayoutY(14.0);
        ipDevice.setPrefHeight(24.0);
        ipDevice.setPrefWidth(144.0);
        ipDevice.setId("ipDevice" + id);
        ipDevice.setText("192.168.250.30");
        ipDevice.setStyle("-fx-text-inner-color: black;");
        ipDevice.setStyle("-fx-background-color: #eda678;");

        portDevice = new TextField();
        portDevice.setLayoutX(566.0);
        portDevice.setLayoutY(14.0);
        portDevice.setPrefHeight(24.0);
        portDevice.setPrefWidth(54.0);
        portDevice.setId("portDevice" + id);
        portDevice.setText("161");
        portDevice.setStyle("-fx-text-inner-color: black;");
        portDevice.setStyle("-fx-background-color: #eda678;");

        toggleButton = new JFXToggleButton();
        toggleButton.setToggleGroup(toggleGroup);
        toggleButton.setLayoutX(223.0);
        toggleButton.setPrefHeight(24.0);
        toggleButton.setPrefWidth(57.0);
        toggleButton.setId("toggleButton" + id);
        toggleButton.setText("On");
        toggleButton.getStylesheets().add(Objects.requireNonNull(Device.class.getResource("/ru/rtrs/rtrs_crack/styleTest.css")).toExternalForm());
        toggleButton.setStyle(".jfx-toggle-button");


        comboBox = new ComboBox<>();
        comboBox.setLayoutX(4.0);
        comboBox.setLayoutY(17.0);
        comboBox.setPrefHeight(24.0);
        comboBox.setPrefWidth(213.0);
        comboBox.getItems().addAll(Arrays.asList(TypeDevice.values()));
        comboBox.setPromptText(TypeDevice.Microtec30.toString());
        comboBox.setId("comboBox" + id);
        comboBox.getStylesheets().add(Objects.requireNonNull(Device.class.getResource("/ru/rtrs/rtrs_crack/styleTest.css")).toExternalForm());
        comboBox.setStyle(".combo-box");

        anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(ipDevice);
        anchorPane.getChildren().add(portDevice);
        anchorPane.getChildren().add(pwrDevice);

        anchorPane.getChildren().add(toggleButton);



        return anchorPane;

    }

}
