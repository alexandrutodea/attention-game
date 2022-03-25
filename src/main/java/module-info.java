module com.example.p3finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires java.xml.bind;

    opens com.example.p3finalproject to javafx.fxml, org.hibernate.orm.core;
    opens com.example.p3finalproject.model to org.hibernate.orm.core, javafx.fxml;
    exports com.example.p3finalproject;
    exports com.example.p3finalproject.model;
    exports com.example.p3finalproject.ui;
    exports com.example.p3finalproject.exceptions;
    exports com.example.p3finalproject.server;
    opens com.example.p3finalproject.ui to javafx.fxml;
}