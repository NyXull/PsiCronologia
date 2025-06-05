module com.example.psiorganize {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires javafx.web;
    requires javafx.swing;
    requires org.jsoup;

    opens view to javafx.fxml;
    opens controller to javafx.fxml;

    exports view;
}