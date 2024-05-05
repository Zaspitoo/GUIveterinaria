module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.desktop; // Using transitive to make java.desktop types available to other modules
    requires transitive java.sql;
    requires java.sql.rowset;
    requires javafx.graphics;

    // Ensure that all relevant packages are open to javafx.fxml if you use FXML in them
    opens com.example to javafx.fxml;
    
    // Export com.example if other modules or applications need to access the classes in this package
    exports com.example;

    // If you have more packages, consider opening or exporting them as needed
    // opens com.example.subpackage to javafx.fxml;
    // exports com.example.subpackage;
}
