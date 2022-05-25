module com.ids.trustservicesurfer {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens com.ids.trustservicesurfer to javafx.fxml;
    exports com.ids.trustservicesurfer;
}