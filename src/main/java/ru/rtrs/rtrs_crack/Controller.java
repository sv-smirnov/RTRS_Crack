package ru.rtrs.rtrs_crack;

import com.jfoenix.controls.JFXToggleButton;
import com.oneandone.snmpman.Snmpman;
import com.oneandone.snmpman.SnmpmanAgent;
import com.oneandone.snmpman.configuration.AgentConfiguration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Paint;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Controller {

    private boolean keyPwrBtn;
    private Snmpman snmpman;


    @FXML
    private ImageView antennaImage;

    @FXML
    private ImageView closeBtn;

    @FXML
    private TextField mayakIp;

    @FXML
    private TextField mayakPort;

    @FXML
    private TextField mayak_power;

    @FXML
    private AnchorPane mayak_settings;

    @FXML
    private JFXToggleButton mayak_toggle;

    @FXML
    private ImageView powerButton;

    @FXML
    private AnchorPane primaryPane;

    @FXML
    private TextField rrIp;

    @FXML
    private TextField rrPort;

    @FXML
    private TextField rr_power;

    @FXML
    private AnchorPane rr_settings;

    @FXML
    private JFXToggleButton rr_toggle;

    @FXML
    private AnchorPane settingsBar;

    @FXML
    private ImageView settingsButton;

    @FXML
    private TextField sxIp;

    @FXML
    private TextField sxPort;

    @FXML
    private AnchorPane sx_settings;

    @FXML
    private JFXToggleButton sx_toggle;

    @FXML
    private TextField tseIp;

    @FXML
    private TextField tsePort;

    @FXML
    private AnchorPane tse_settings;

    @FXML
    private JFXToggleButton tse_toggle;

    @FXML
    private TextField tv_power;

    @FXML
    private TextField uaxteIp;

    @FXML
    private TextField uaxtePort;

    @FXML
    private AnchorPane uaxte_settings;

    @FXML
    private JFXToggleButton uaxte_toggle;

    @FXML
    private TextField vfmIp;

    @FXML
    private TextField vfmPort;

    @FXML
    private TextField vfm_power;

    @FXML
    private AnchorPane vfm_settings;

    @FXML
    private JFXToggleButton vfm_toggle;

    @FXML
    void initialize() throws MalformedURLException {

        keyPwrBtn = false;

        primaryPane.setBackground(Background.EMPTY);

        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerWhite.png"))));
        settingsButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/settings.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaWhite.png"))));
        closeBtn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/exitOrange.png"))));

        tse_toggle.setSelected(true);
        sx_toggle.setSelected(true);
        uaxte_toggle.setSelected(true);
        rr_toggle.setSelected(true);
        vfm_toggle.setSelected(true);
        mayak_toggle.setSelected(true);

        // действия по переключению кнопок JFXToggleButton
        tse_toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(tse_toggle.isSelected()) {
                    setVisibleTSE(true);
                    tse_toggle.setText("On");
                    tse_toggle.setTextFill(Paint.valueOf("#eda678"));
                } else {
                    setVisibleTSE(false);
                    tse_toggle.setText("Off");
                    tse_toggle.setTextFill(Paint.valueOf("#fafafa"));
                }
            }
        });

        sx_toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(sx_toggle.isSelected()) {
                    setVisibleSX(true);
                    sx_toggle.setText("On");
                    sx_toggle.setTextFill(Paint.valueOf("#eda678"));
                } else {
                    setVisibleSX(false);
                    sx_toggle.setText("Off");
                    sx_toggle.setTextFill(Paint.valueOf("#fafafa"));
                }
            }
        });

        uaxte_toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(uaxte_toggle.isSelected()) {
                    setVisibleUAXTE(true);
                    uaxte_toggle.setText("On");
                    uaxte_toggle.setTextFill(Paint.valueOf("#eda678"));
                } else {
                    setVisibleUAXTE(false);
                    uaxte_toggle.setText("Off");
                    uaxte_toggle.setTextFill(Paint.valueOf("#fafafa"));
                }
            }
        });

        rr_toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(rr_toggle.isSelected()) {
                    setVisibleRR(true);
                    rr_toggle.setText("On");
                    rr_toggle.setTextFill(Paint.valueOf("#eda678"));
                } else {
                    setVisibleRR(false);
                    rr_toggle.setText("Off");
                    rr_toggle.setTextFill(Paint.valueOf("#fafafa"));
                }
            }
        });

        vfm_toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(vfm_toggle.isSelected()) {
                    setVisibleVfm(true);
                    vfm_toggle.setText("On");
                    vfm_toggle.setTextFill(Paint.valueOf("#eda678"));
                } else {
                    setVisibleVfm(false);
                    vfm_toggle.setText("Off");
                    vfm_toggle.setTextFill(Paint.valueOf("#fafafa"));
                }
            }
        });

        mayak_toggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(mayak_toggle.isSelected()) {
                    setVisibleMayak(true);
                    mayak_toggle.setText("On");
                    mayak_toggle.setTextFill(Paint.valueOf("#eda678"));
                } else {
                    setVisibleMayak(false);
                    mayak_toggle.setText("Off");
                    mayak_toggle.setTextFill(Paint.valueOf("#fafafa"));
                }
            }
        });

    }

    private void setVisibleTSE(boolean b) {
        tse_settings.setVisible(b);
    }
    private void setVisibleSX(boolean b) {
        sx_settings.setVisible(b);
    }
    private void setVisibleUAXTE(boolean b) {
        uaxte_settings.setVisible(b);
    }
    private void setVisibleRR(boolean b) {
        rr_settings.setVisible(b);
    }
    private void setVisibleVfm(boolean b) {
        vfm_settings.setVisible(b);
    }
    private void setVisibleMayak(boolean b) {
        mayak_settings.setVisible(b);
    }


    @FXML
    void handleImageAction(MouseEvent event) throws MalformedURLException {
        if (event.getTarget() == powerButton) {
            if (!keyPwrBtn){
                keyPwrBtn = true;
                start(event);
            } else {
                keyPwrBtn = false;
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
        checkNumField(tv_power);
        checkNumField(tsePort);
        checkNumField(sxPort);
        checkNumField(uaxtePort);
        checkIPField(sxIp);
        checkIPField(tseIp);
        checkIPField(uaxteIp);

        List<SnmpmanAgent> listSnmpAgent = new ArrayList<SnmpmanAgent>();
        if(tse_toggle.isSelected()) {
            listSnmpAgent.add(getSnmpmanAgent("tse", getWalk(tv_power, "tse"), tseIp, tsePort));
        }
        if(sx_toggle.isSelected()) {
            listSnmpAgent.add(getSnmpmanAgent("sx", getWalk(tv_power, "sx"), sxIp, sxPort));
        }
        if(uaxte_toggle.isSelected()) {
            listSnmpAgent.add(getSnmpmanAgent("uaxte", getWalk(tv_power, "uaxte"), uaxteIp, uaxtePort));
        }
        if(rr_toggle.isSelected()) {
            listSnmpAgent.add(getSnmpmanAgent("rr", getWalk(rr_power, "rr"), rrIp, rrPort));
        }
        if(vfm_toggle.isSelected()) {
            listSnmpAgent.add(getSnmpmanAgent("vfm", getWalk(vfm_power, "vfm"), vfmIp, vfmPort));
        }
        if(mayak_toggle.isSelected()) {
            listSnmpAgent.add(getSnmpmanAgent("mayak", getWalk(mayak_power, "mayak"), mayakIp, mayakPort));
        }

        snmpman = Snmpman.start(listSnmpAgent);


        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerOn.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaBlue.png"))));

//        System.out.println(snmpman.getAgents().get(0).getSysOID().format());
//        System.out.println(snmpman.getAgents().get(0).getSysOID().toString());


    }

    private File getWalk(TextField pwr, String type) // файл выбирается в зависимости от типа устройства и мощности
    {
        String filepath;
        if (Integer.parseInt(pwr.getText()) <= 0) {
            tv_power.setText("Enter nominal power");
            return null;
        } else if (Integer.parseInt(pwr.getText()) < 50) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "10v2.walk";
        } else  if (Integer.parseInt(pwr.getText()) < 100) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "50v2.walk";
        } else  if (Integer.parseInt(pwr.getText()) < 250) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "100v2.walk";
        } else  if (Integer.parseInt(pwr.getText()) < 500) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "250v2.walk";
        } else {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "500v2.walk";
        }
        File walk = new File(filepath);
        return walk;
    }

    private SnmpmanAgent getSnmpmanAgent(String name, File walk, TextField ip, TextField port) {
        AgentConfiguration agentConfiguration = new AgentConfiguration(name, null, walk,
                ip.getText(), Integer.parseInt(port.getText()), "public");
        return new SnmpmanAgent(agentConfiguration);
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