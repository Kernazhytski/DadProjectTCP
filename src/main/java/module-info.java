module com.example.dadprojecttcp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.dadprojecttcp to javafx.fxml;
    exports com.example.dadprojecttcp;
}
