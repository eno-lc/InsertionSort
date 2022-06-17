module com.example.insertionvis {
    requires javafx.controls;
    requires javafx.fxml;


    opens InsertionSort to javafx.fxml;
    exports InsertionSort;
}