module com.example.hotelreversation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.hotelreversation to javafx.fxml;
    exports com.example.hotelreversation;
}