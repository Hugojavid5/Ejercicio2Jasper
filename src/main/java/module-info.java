module org.hugo.dein.jasperejercicio2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hugo.dein.jasperejercicio2 to javafx.fxml;
    exports org.hugo.dein.jasperejercicio2;
}