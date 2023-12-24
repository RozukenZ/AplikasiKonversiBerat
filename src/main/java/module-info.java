module com.kalkulatorberat.kkb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.kalkulatorberat.kkb to javafx.fxml;
    exports com.kalkulatorberat.kkb;
}