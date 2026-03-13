module code {
    requires javafx.controls;
    requires javafx.fxml;
//    requires code;
//    requires javafx.web;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
//    requires org.kordamp.ikonli.javafx;
//    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;
    opens code.initialise to javafx.fxml;
    opens code.gui to javafx.fxml;
    exports code.gui;
}