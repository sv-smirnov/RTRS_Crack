module ru.rtrs.rtrs_crack {
    requires javafx.controls;
    requires javafx.fxml;
    requires snmpman;


    opens ru.rtrs.rtrs_crack to javafx.fxml;
    exports ru.rtrs.rtrs_crack;
}