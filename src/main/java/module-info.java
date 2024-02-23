module com.example.cockteleriaapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens Modelos to javafx.base;

    opens com.example.cockteleriaapi to javafx.fxml;
    exports com.example.cockteleriaapi;
}