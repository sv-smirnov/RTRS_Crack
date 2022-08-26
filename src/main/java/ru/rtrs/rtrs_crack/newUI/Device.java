package ru.rtrs.rtrs_crack.newUI;

import com.jfoenix.controls.JFXToggleButton;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.Arrays;
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

    public TypeDevice getTypeDevice() {
        return comboBox.getValue();
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

        comboBox = new ComboBox<>();
        comboBox.setLayoutX(0.0);
        comboBox.setLayoutY(14.0);
        comboBox.setPrefHeight(24.0);
        comboBox.setPrefWidth(200.0);
        comboBox.getItems().addAll(Arrays.asList(TypeDevice.values()));
        comboBox.setPromptText("Select hardware");
        comboBox.setValue(TypeDevice.values()[0]);
        comboBox.setId("comboBox" + id);
        comboBox.getStylesheets().add(Objects.requireNonNull(Device.class.getResource("/ru/rtrs/rtrs_crack/styleTest.css")).toExternalForm());
        comboBox.setStyle(".combo-box");

        toggleButton = new JFXToggleButton();
        toggleButton.setLayoutX(200.0);
        toggleButton.setPrefHeight(24.0);
        toggleButton.setPrefWidth(100.0);
        toggleButton.setId("toggleButton" + id);
        toggleButton.setSelected(true);
        toggleButton.setTextFill(Paint.valueOf("#eda678"));
        toggleButton.setText("On");
        toggleButton.getStylesheets().add(Objects.requireNonNull(Device.class.getResource("/ru/rtrs/rtrs_crack/styleTest.css")).toExternalForm());
        toggleButton.setStyle(".jfx-toggle-button");
        toggleButton.setOnAction(e -> {
            ChangeToggle();
        });

        anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(51.0);
        anchorPane.setPrefWidth(600.0);
        anchorPane.setId("pane" + id);
        anchorPane.setStyle("");
        anchorPane.getChildren().add(comboBox);
        anchorPane.getChildren().add(ipDevice);
        anchorPane.getChildren().add(portDevice);
        anchorPane.getChildren().add(pwrDevice);
        anchorPane.getChildren().add(toggleButton);

        comboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue == TypeDevice.RS_TSE800) {
                pwrDevice.setText("none");
                pwrDevice.setDisable(true);
            }
        });

        return anchorPane;

    }

    private void ChangeToggle() {
        if(toggleButton.isSelected()) {
            comboBox.setDisable(false);
            pwrDevice.setDisable(false);
            ipDevice.setDisable(false);
            portDevice.setDisable(false);
            setToggle(toggleButton, "#eda678", "On");
        } else {
            comboBox.setDisable(true);
            pwrDevice.setDisable(true);
            ipDevice.setDisable(true);
            portDevice.setDisable(true);
            setToggle(toggleButton, "#fafafa","Off");
        }
    }

    private void setToggle(JFXToggleButton tg, String color, String text) {
        tg.setTextFill(Paint.valueOf(color));
        tg.setText(text);
    }



}
