module org.example.todoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires io.github.cdimascio.dotenv.java;
    requires java.net.http;
    requires jdk.jconsole;

    opens org.example.todoapp to javafx.fxml;
    exports org.example.todoapp;
    exports org.example.todoapp.model;
    opens org.example.todoapp.model to javafx.fxml;
    opens org.example.todoapp.controller to javafx.fxml;
}