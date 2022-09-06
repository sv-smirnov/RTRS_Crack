module ru.rtrs.rtrs_crack {
    requires javafx.controls;
    requires javafx.fxml;
    requires snmpman;
    requires com.jfoenix;
    requires org.snmp4j;
    requires lombok;


    opens ru.rtrs.rtrs_crack to javafx.fxml;
    exports ru.rtrs.rtrs_crack;
    exports ru.rtrs.rtrs_crack.newUI;
    opens ru.rtrs.rtrs_crack.newUI to javafx.fxml;
}