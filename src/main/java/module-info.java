module com.example.psiorganize {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires javafx.web;
    requires javafx.swing;
    
    opens view to javafx.fxml;
    opens controller to javafx.fxml;

    exports view;
}