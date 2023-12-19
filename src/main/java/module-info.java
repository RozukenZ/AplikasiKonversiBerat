module com.kalkulatorberat.kkb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kalkulatorberat.kkb to javafx.fxml;
    exports com.kalkulatorberat.kkb;
}