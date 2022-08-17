package ru.rtrs.rtrs_crack;

import com.oneandone.snmpman.Snmpman;
import com.oneandone.snmpman.SnmpmanAgent;
import com.oneandone.snmpman.configuration.AgentConfiguration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField power;

    @FXML
    private ImageView powerButton;

    @FXML
    private ImageView closeBtn;

    @FXML
    private AnchorPane primaryPane;

    @FXML
    private AnchorPane settingsBar;

    @FXML
    private ImageView settingsButton;

    @FXML
    private ImageView antennaImage;

    @FXML
    private TextField sxIp;

    @FXML
    private TextField sxPort;

    @FXML
    private AnchorPane topBar;

    @FXML
    private TextField tseIp;

    @FXML
    private TextField tsePort;

    @FXML
    private TextField uaxteIp;

    @FXML
    private TextField uaxtePort;

    @FXML
    void initialize() throws MalformedURLException {

        primaryPane.setBackground(Background.EMPTY);

        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerWhite.png"))));
        settingsButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/settings.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaWhite.png"))));
        closeBtn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/closeBtn.png"))));

        assert power != null : "fx:id=\"power\" was not injected: check your FXML file 'primary.fxml'.";
        assert powerButton != null : "fx:id=\"powerButton\" was not injected: check your FXML file 'primary.fxml'.";
        assert settingsBar != null : "fx:id=\"settingsBar\" was not injected: check your FXML file 'primary.fxml'.";
        assert settingsButton != null : "fx:id=\"settingsButton\" was not injected: check your FXML file 'primary.fxml'.";
        assert sxIp != null : "fx:id=\"sxIp\" was not injected: check your FXML file 'primary.fxml'.";
        assert sxPort != null : "fx:id=\"sxPort\" was not injected: check your FXML file 'primary.fxml'.";
        assert topBar != null : "fx:id=\"topBar\" was not injected: check your FXML file 'primary.fxml'.";
        assert tseIp != null : "fx:id=\"tseIp\" was not injected: check your FXML file 'primary.fxml'.";
        assert tsePort != null : "fx:id=\"tsePort\" was not injected: check your FXML file 'primary.fxml'.";
        assert uaxteIp != null : "fx:id=\"uaxteIp\" was not injected: check your FXML file 'primary.fxml'.";
        assert uaxtePort != null : "fx:id=\"uaxtePort\" was not injected: check your FXML file 'primary.fxml'.";

    }

    private boolean key=false;

    private String filepath;

    private Snmpman snmpman;


    @FXML
    void handleImageAction(MouseEvent event) throws MalformedURLException {
        if (event.getTarget() == powerButton) {
            if (!key){
                key = true;
                start(event);
            } else if (key) {
                key = false;
                stop(event);
            }
        } else if (event.getTarget() == settingsButton) {
            if (settingsBar.isVisible()) {
                settingsBar.setVisible(false);
            } else if (!settingsBar.isVisible()) {
                settingsBar.setVisible(true);
            }
        } else if (event.getTarget() == closeBtn) {
            System.exit(0);
        }
    }

    public void start(Event actionEvent) throws MalformedURLException {
        checkNumField(power);
        checkNumField(tsePort);
        checkNumField(sxPort);
        checkNumField(uaxtePort);
        checkIPField(sxIp);
        checkIPField(tseIp);
        checkIPField(uaxteIp);



        if (Integer.parseInt(power.getText()) == 0) {
            power.setText("Enter nominal power");
        } else if ((0 < Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 50)) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/UNI10v2.walk";
        } else  if ((50 <= Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 100)) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni50v2.walk";
        } else  if ((100 <= Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 250)) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni100v2.walk";
        } else  if ((250 <= Integer.parseInt(power.getText())) & (Integer.parseInt(power.getText()) < 500)) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni250v2.walk";
        } else {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/uni500v2.walk";
        }
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
        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerOn.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaBlue.png"))));


    }
    public void stop(Event actionEvent) {
        snmpman.stop();
        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerOff.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaRed.png"))));

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

    private void checkIPField(TextField textField){
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("^(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
                        "\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
                        "\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
                        "\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")) {
                    textField.setText("192.168.250.30");
                }
            }
        });
    }


}