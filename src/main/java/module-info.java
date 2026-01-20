module fr.efrei.alsi54 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;

    opens fr.efrei.alsi54 to javafx.fxml;

    exports fr.efrei.alsi54;
}