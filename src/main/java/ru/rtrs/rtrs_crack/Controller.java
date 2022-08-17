package ru.rtrs.rtrs_crack;

import com.oneandone.snmpman.Snmpman;
import com.oneandone.snmpman.SnmpmanAgent;
import com.oneandone.snmpman.configuration.AgentConfiguration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private TextField power;
    @FXML
    private TextField tseIp;
    @FXML
    private TextField tsePort;
    @FXML
    private TextField sxIp;
    @FXML
    private TextField sxPort;
    @FXML
    private TextField uaxteIp;
    @FXML
    private TextField uaxtePort;
    @FXML
    private Label statusLabel;
    @FXML
    private ImageView settingsImage;

    private String filepath;

    private Snmpman snmpman;

    public void start(ActionEvent actionEvent) {
        checkNumField(power);
        checkNumField(tsePort);
        checkNumField(sxPort);
        checkNumField(uaxtePort);

        if (Integer.parseInt(power.getText()) == 0) {power.setText("Enter nominal power");}
        else if ((0 < Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 50)) {filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/UNI10v2.walk";}
        else  if ((50 <= Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 100)) {filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni50v2.walk";}
        else  if ((100 <= Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 250)) {filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni100v2.walk";}
        else  if ((250 <= Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 500)) {filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni250v2.walk";}
        else {filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni500v2.walk";}
        File walk = new File(filepath);

        AgentConfiguration tseConfiguration = new AgentConfiguration("tse", null, walk,
                tseIp.getText(), Integer.parseInt(tsePort.getText()), "public");
        SnmpmanAgent tseAgent = new SnmpmanAgent(tseConfiguration);

        AgentConfiguration sxConfiguration = new AgentConfiguration("sx", null, walk,
                sxIp.getText(), Integer.parseInt(sxPort.getText()), "public");
        SnmpmanAgent sxAgent = new SnmpmanAgent(sxConfiguration);

        AgentConfiguration uaxteConfiguration = new AgentConfiguration("uaxte", null, walk,
                uaxteIp.getText(), Integer.parseInt(uaxtePort.getText()), "public");
        SnmpmanAgent uaxteAgent = new SnmpmanAgent(uaxteConfiguration);

        List<SnmpmanAgent> listSnmpAgent = new ArrayList<SnmpmanAgent>();
        listSnmpAgent.add(0, tseAgent);
        listSnmpAgent.add(1, sxAgent);
        listSnmpAgent.add(2,uaxteAgent);
        snmpman = Snmpman.start(listSnmpAgent);

        stopButton.setDisable(false);
        startButton.setDisable(true);
        statusLabel.setTextFill(Color.GREEN);
        statusLabel.setText("Simulation running...");


    }
    public void stop(ActionEvent actionEvent) {
        snmpman.stop();
        startButton.setDisable(false);
        stopButton.setDisable(true);
        statusLabel.setTextFill(Color.RED);
        statusLabel.setText("Simulation stopped");
    }

    private void checkNumField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", "161"));
                }
            }
        });
    }

    @FXML
    private void handleImageAction(MouseEvent event) {
        if (event.getTarget() = settingsImage) {
            if (
                snmpman.
            )
        }
    }
}