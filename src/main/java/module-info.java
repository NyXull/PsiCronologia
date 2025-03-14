module com.example.psiorganize {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.psiorganize to javafx.fxml;
    exports com.example.psiorganize;
}