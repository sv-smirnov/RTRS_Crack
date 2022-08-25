package ru.rtrs.rtrs_crack.newUI;

import com.oneandone.snmpman.Snmpman;
import com.oneandone.snmpman.SnmpmanAgent;
import com.oneandone.snmpman.configuration.AgentConfiguration;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ControllerTest {

    private boolean keyPwrBtn;
    private Snmpman snmpman;

    @FXML
    private VBox vbox_device;
    @FXML
    private ImageView antennaImage;

    @FXML
    private ImageView plus;
    @FXML
    private ImageView closeBtn;
    @FXML
    private ImageView powerButton;
    @FXML
    private AnchorPane primaryPane;
    @FXML
    private AnchorPane settingsBar;
    @FXML
    private ImageView settingsButton;
    @FXML
    private AnchorPane infoBar;
    @FXML
    private Label label_info;

    private HashMap<String, Device> deviceList = new HashMap<String, Device>();


    @FXML
    void initialize() {

        keyPwrBtn = false;

        primaryPane.setBackground(Background.EMPTY);

        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerWhite.png"))));
        settingsButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/settings.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaWhite.png"))));
        closeBtn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/exitOrange.png"))));
        plus.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/plus.png"))));

        infoBar.setVisible(false);
        label_info.setText("""
                RTRS_Crack

                Описание проекта:
                Эта программа является SNMP-симулятором и поддерживает возможность 
                запуска сразу нескольких SNMP-агентов, с различными настройками.\s
                По сути это бесплатная версия таких программ как iReasoning, 
                Verax и Gambit.
                
                Программа создавалась специально под систему 
                мониторинга РТРС и поддерживает эмуляцию передатчиков 
                Rohde&Schwarz Tx8 и GatesAir UAXTE (шаблоны пополняются).
                
                Для адаптации программы под другие устройства необходимо 
                создать соответствующий snmpwalk файл с нужными нам параметрами.
                Оболочка приложения клиента сделана на JavaFx.

                Особенности проекта:
                - Snmpman
                - JavaFx

                Запуск приложения:
                1. Создать сетевые адаптеры для запускаемых устройств
                2. Запускаем клиент, настраиваем параметры SNMP агентов
                3. Запускаем эмуляцию

                https://github.com/sv-smirnov/RTRS_Crack""");

        vbox_device.getChildren().add(new Device(deviceList.size()).getAnchorPane());
    }

    @FXML
    void handleImageAction(MouseEvent event) throws MalformedURLException { // Реакция на нажатие на кнопки
        if (event.getTarget() == powerButton) { // Кнопка Старт
            if (!keyPwrBtn){
                keyPwrBtn = true;
                start(event);
            } else {
                keyPwrBtn = false;
                stop(event);
            }
        } else if (event.getTarget() == settingsButton) { // Кнопка переключения между настройками и информацией о программе
            if (settingsBar.isVisible()) {
                settingsBar.setVisible(false);
                infoBar.setVisible(true);
                settingsButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/info.png"))));
            } else if (!settingsBar.isVisible()) {
                settingsBar.setVisible(true);
                infoBar.setVisible(false);
                settingsButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/settings.png"))));
            }
        } else if (event.getTarget() == plus) {
            vbox_device.getChildren().add(new Device(deviceList.size()).getAnchorPane());

        } else if (event.getTarget() == closeBtn) {
            System.exit(0);
        }
    }

    public void start(Event actionEvent) throws MalformedURLException {

        List<SnmpmanAgent> listSnmpAgent = new ArrayList<SnmpmanAgent>();

        deviceList.forEach((k, v) -> {
            v.setSettings(); // записываем настройки в <Device>device
            if(v.getToggleButton().isSelected()) {
                listSnmpAgent.add(getSnmpmanAgent(v.getTypeDevice().toString() + "." + k,
                        getWalk(v),
                        v.getIpDevice(),
                        v.getPortDevice()));
            }
        });

        snmpman = Snmpman.start(listSnmpAgent);


        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/powerOn.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaBlue.png"))));

    }

    private File getWalk(Device v) // файл выбирается в зависимости от типа устройства и мощности
    {

        int pwr = Integer.parseInt(v.getPortDevice().getText());
        String type = v.getTypeDevice().toString().substring(v.getTypeDevice().toString().indexOf('_') + 1);

        String filepath;
        if (pwr <= 0) {
            v.getPwrDevice().setText("Enter nominal power");
            return null;
        } else if (pwr < 50) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "10v2.walk";
        } else  if (pwr < 100) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "50v2.walk";
        } else  if (pwr < 250) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "100v2.walk";
        } else  if (pwr < 500) {
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


}