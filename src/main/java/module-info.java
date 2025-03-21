module com.example.psiorganize {
    requires javafx.controls;
    requires javafx.fxml;

    opens view to javafx.fxml;
    opens controller to javafx.fxml;

    exports view;
}
