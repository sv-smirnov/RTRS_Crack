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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.*;

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
    public static EnumMap<TypeDevice, String[]> powerDevice = new EnumMap<>(TypeDevice.class);


    @FXML
    void initialize() {

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

        createPowerDevice();
        addDeviceList();
    }

    @FXML
    void handleImageAction(MouseEvent event) throws IOException, InterruptedException { // Реакция на нажатие на кнопки
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
            addDeviceList();
        } else if (event.getTarget() == closeBtn) {
            System.exit(0);
        }
    }

    private void addDeviceList() {
        Device device = new Device(deviceList.size());
        deviceList.put(Integer.toString(deviceList.size()), device);
        vbox_device.getChildren().add(device.getAnchorPane());
    }

    public void start(Event actionEvent) throws IOException, InterruptedException {
        List<SnmpmanAgent> listSnmpAgent = new ArrayList<SnmpmanAgent>();
        List<String> alias= new ArrayList<String>();
        deviceList.forEach((k, v) -> {
            v.setSettings(); // записываем настройки в <Device>device
            if(v.getToggleButton().isSelected()) {
                listSnmpAgent.add(getSnmpmanAgent(v.getTypeDevice().toString() + "." + k,
                        getWalk(v),
                        v.getIpDevice(),
                        v.getPortDevice()));
                alias.add(v.getIpDevice().getText());
            }
        });
        IpConfig.changeIp();
        IpConfig.addAlias(alias);
        Thread.sleep(5000);
        snmpman = Snmpman.start(listSnmpAgent);
        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/rtrs_green.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaBlue.png"))));

    }

    private File getWalk(Device v) // файл выбирается в зависимости от типа устройства и мощности
    {
        int pwr = Integer.parseInt(v.getPwrDevice().getValue());
        String type = v.getTypeDevice().
                toString().substring(v.getTypeDevice().toString().indexOf('_') + 1); // Тип оборудования после нижнего подчеркивания TypeDevice
        String filepath;
        if (pwr <= 0) {
            v.getPwrDevice().setValue("Enter nominal power");
            return null;
        } else if (pwr < 50) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "10v2.walk";
        } else  if (pwr < 100) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "50v2.walk";
        } else  if (pwr < 250) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "100v2.walk";
        } else  if (pwr < 500) {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "250v2.walk";
        } else if (pwr < 1000){
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "500v2.walk";
        } else if (pwr < 2000){
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "1000v2.walk";
        } else {
            filepath = "src/main/java/ru/rtrs/rtrs_crack/walk/" + type + "2000v2.walk";
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
        powerButton.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/rtrs_orange.png"))));
        antennaImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ru/rtrs/rtrs_crack/images/antennaWhite.png"))));

    }

    public void createPowerDevice() {
        powerDevice.put(TypeDevice.AlmazAntey_HCDVB, new String[]{ "100", "500" });
        powerDevice.put(TypeDevice.GatesAir_UAXTE, new String[]{ "10", "50", "250", "500" });
        powerDevice.put(TypeDevice.Harris_UAX, new String[]{ "10", "50", "100", "250", "500" });
        powerDevice.put(TypeDevice.Harris_ULX, new String[]{ "1000", "2000" });
        powerDevice.put(TypeDevice.Microtec_TF, new String[]{ "30", "100", "250", "500", "1000" });
        powerDevice.put(TypeDevice.Microtec_TTUD, new String[]{ "200", "250", "500" });
        powerDevice.put(TypeDevice.RS_SxSLx, new String[]{ "10", "50", "100", "250", "500" });
        powerDevice.put(TypeDevice.RS_THU9x, new String[]{ "1000", "2000" });
        powerDevice.put(TypeDevice.RS_TLU9x, new String[]{ "50", "100", "250" });
        powerDevice.put(TypeDevice.RS_TSE800, new String[]{ "none" });
        powerDevice.put(TypeDevice.Vigintos_TVD, new String[]{ "50", "100" });
    }


}