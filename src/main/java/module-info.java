module com.example.psiorganize {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires javafx.web;
    requires javafx.swing;
    requires org.jsoup;
    requires jakarta.xml.bind;
    requires org.docx4j.core;
    requires docx4j_ImportXHTML;

    opens view to javafx.fxml;
    opens controller to javafx.fxml;

    exports view;
}
