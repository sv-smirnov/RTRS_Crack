package ru.rtrs.rtrs_crack;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

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

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public Device(int id) {
        anchorPane = addDevice(id);
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
        power = getPwrDevice();
        ip = getIpDevice();
        port = getPortDevice();
        typeDevice = getTypeDevice();

    }

    public String getPwrDevice() {
        return pwrDevice.getText();
    }

    public String getIpDevice() {
        return ipDevice.getText();
    }

    public String getPortDevice() {
        return portDevice.getText();
    }

    public boolean getToggleButton() {
        return toggleButton.isSelected();
    }

    public TypeDevice getComboBox() {
        return comboBox.getSelectionModel().getSelectedItem();
    }

    public AnchorPane addDevice(Integer id) {

        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(51.0);
        anchorPane.setPrefWidth(634.0);
        anchorPane.setId("pane" + id);

        pwrDevice = new TextField();
        pwrDevice.setLayoutX(308.0);
        pwrDevice.setLayoutY(14.0);
        pwrDevice.setPrefHeight(24.0);
        pwrDevice.setPrefWidth(69.0);
        pwrDevice.setId("pwrDevice" + id);
        pwrDevice.setText("100");

        ipDevice = new TextField();
        ipDevice.setLayoutX(395.0);
        ipDevice.setLayoutY(14.0);
        ipDevice.setPrefHeight(24.0);
        ipDevice.setPrefWidth(144.0);
        ipDevice.setId("ipDevice" + id);
        ipDevice.setText("192.168.250.30");

        portDevice = new TextField();
        portDevice.setLayoutX(566.0);
        portDevice.setLayoutY(14.0);
        portDevice.setPrefHeight(24.0);
        portDevice.setPrefWidth(54.0);
        portDevice.setId("portDevice" + id);
        portDevice.setText("161");

        toggleButton = new JFXToggleButton();
        toggleButton.setLayoutX(223.0);
        toggleButton.setPrefHeight(24.0);
        toggleButton.setPrefWidth(57.0);
        toggleButton.setId("toggleButton" + id);
        toggleButton.setText("On");
        toggleButton.getStylesheets().add(Objects.requireNonNull(Device.class.getResource("/ru/rtrs/rtrs_crack/style.css")).toExternalForm());
        toggleButton.setStyle(".jfx-toggle-button");


        comboBox = new ComboBox<>();
        comboBox.setLayoutX(4.0);
        comboBox.setLayoutY(17.0);
        comboBox.setPrefHeight(24.0);
        comboBox.setPrefWidth(213.0);
        comboBox.getItems().addAll(Arrays.asList(TypeDevice.values()));
        comboBox.setPromptText(TypeDevice.Microtec30.toString());
        comboBox.setId("comboBox" + id);
        comboBox.getStylesheets().add(Objects.requireNonNull(Device.class.getResource("/ru/rtrs/rtrs_crack/style.css")).toExternalForm());
        comboBox.setStyle(".combo-box");

        anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(ipDevice);
        anchorPane.getChildren().add(portDevice);
        anchorPane.getChildren().add(pwrDevice);

        anchorPane.getChildren().add(toggleButton);



        return anchorPane;

    }

}
