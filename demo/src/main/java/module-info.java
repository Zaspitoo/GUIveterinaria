module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    // Asegura que todos los paquetes relevantes están abiertos a javafx.fxml, si usas FXML en ellos
    opens com.example to javafx.fxml;
    
    // Exporta com.example si otros módulos o aplicaciones necesitan acceder a las clases en este paquete
    exports com.example;

    // Si tienes más paquetes, considera abrirlos o exportarlos según sea necesario
    // opens com.example.subpackage to javafx.fxml;
    // exports com.example.subpackage;
}
