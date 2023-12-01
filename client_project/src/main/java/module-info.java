module ca.cmpt213 {
    requires transitive javafx.controls;
    requires com.google.gson;
    requires javafx.fxml;

    opens ca.cmpt213 to javafx.fxml, com.google.gson;
    exports ca.cmpt213;
}
