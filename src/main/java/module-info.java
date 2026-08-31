module org.zeki.rolgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires static lombok;
    requires java.desktop;

    opens org.zeki.rolgame to javafx.fxml;
    exports org.zeki.rolgame;
    exports org.zeki.rolgame.controller;
    opens org.zeki.rolgame.controller to javafx.fxml;
    exports org.zeki.rolgame.model.ataque;
    opens org.zeki.rolgame.model.ataque to javafx.fxml;
    exports org.zeki.rolgame.service;
    opens org.zeki.rolgame.service to javafx.fxml;
}