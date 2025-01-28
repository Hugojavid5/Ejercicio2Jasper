module org.hugo.dein.jasperejercicio2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires net.sf.jasperreports.core;


    opens org.hugo.dein.jasperejercicio2 to javafx.fxml;
    exports org.hugo.dein.jasperejercicio2;
}