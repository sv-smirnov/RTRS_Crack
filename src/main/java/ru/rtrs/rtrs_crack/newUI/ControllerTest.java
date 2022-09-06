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

        System.out.println("start");

        keyPwrBtn = false;

        primaryPane.setBackground(Background.EMPTY);

        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/rtrs_orange.png"))));
        settingsButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/settings.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaWhite.png"))));
        closeBtn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/close_red.png"))));
        plus.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/plus.png"))));

        infoBar.setVisible(false);
        label_info.setText("" +
                "RTRS_Crack\n" +
                "Описание проекта:\n" +
                "Эта программа является SNMP-симулятором и поддерживает возможность \n" +
                "запуска сразу нескольких SNMP-агентов, с различными настройками.\n " +
                "По сути это бесплатная версия таких программ как iReasoning,\n " +
                "Verax и Gambit.\n " +
                "Программа создавалась специально под систему \n " +
                "мониторинга РТРС и поддерживает эмуляцию передатчиков \n " +
                "Rohde&Schwarz Tx8 и GatesAir UAXTE (шаблоны пополняются).\n " +
                "Для адаптации программы под другие устройства необходимо\n " +
                "создать соответствующий snmpwalk файл с нужными нам параметрами.\n " +
                "Оболочка приложения клиента сделана на JavaFx.\n " +
                "Особенности проекта:\n " +
                "- Snmpman\n " +
                "- JavaFx\n " +
                "Запуск приложения:\n " +
                "1. Создать сетевые адаптеры для запускаемых устройств\n " +
                "2. Запускаем клиент, настраиваем параметры SNMP агентов\n " +
                "3. Запускаем эмуляцию\n " +
                "https://github.com/sv-smirnov/RTRS_Crack");
        Device device = new Device(deviceList.size());
        deviceList.put("0", device);
        vbox_device.getChildren().add(device.getAnchorPane());
    }

    @FXML
    void handleImageAction(MouseEvent event) throws MalformedURLException { // Реакция на нажатие на кнопки
        if (event.getTarget() == powerButton) { // Кнопка Старт
            if (!keyPwrBtn){
                keyPwrBtn = true;
                settingsBar.getChildren().get(0).setDisable(true);
                start(event);
            } else {
                keyPwrBtn = false;
                stop(event);
                settingsBar.getChildren().get(0).setDisable(false);

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
            System.out.println(deviceList);
            Device device = new Device(deviceList.size());
            deviceList.put(Integer.toString(deviceList.size()), device);
            vbox_device.getChildren().add(device.getAnchorPane());
            System.out.println(deviceList);

        } else if (event.getTarget() == closeBtn) {
            System.exit(0);
        }
    }

    public void start(Event actionEvent) throws MalformedURLException {
        System.out.println("start event");

        List<SnmpmanAgent> listSnmpAgent = new ArrayList<SnmpmanAgent>();

        System.out.println(deviceList);
        deviceList.forEach((k, v) -> {
            v.setSettings(); // записываем настройки в <Device>device
            System.out.println(v.getToggleButton().isSelected());
            if(v.getToggleButton().isSelected()) {

                System.out.println("start selected");
                listSnmpAgent.add(getSnmpmanAgent(v.getTypeDevice().toString() + "." + k,
                        getWalk(v),
                        v.getIpDevice(),
                        v.getPortDevice()));
            }
        });

        snmpman = Snmpman.start(listSnmpAgent);


        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/rtrs_green.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaBlue.png"))));

    }

    private File getWalk(Device v) // файл выбирается в зависимости от типа устройства и мощности
    {
        int pwr = Integer.parseInt(v.getPortDevice().getText());
        String type = v.getTypeDevice().
                toString().substring(v.getTypeDevice().toString().indexOf('_') + 1); // Тип оборудования после нижнего подчеркивания TypeDevice

        System.out.println(type);
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
        System.out.println(filepath);
        return walk;
    }

    private SnmpmanAgent getSnmpmanAgent(String name, File walk, TextField ip, TextField port) {
        AgentConfiguration agentConfiguration = new AgentConfiguration(name, null, walk,
                ip.getText(), Integer.parseInt(port.getText()), "public");
        return new SnmpmanAgent(agentConfiguration);
    }

    public void stop(Event actionEvent) {
        snmpman.stop();
        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/rtrs_orange.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaWhite.png"))));

    }


}