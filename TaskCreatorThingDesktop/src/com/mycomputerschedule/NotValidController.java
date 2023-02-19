package com.mycomputerschedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * The Controller for the popup that appears when the edit/new task fields are invalid.
 */
public class NotValidController {

    public void setS(Stage s) {
        this.s = s;
    }

    private Stage s;
    @FXML
    private Button okButton;


    /**
     * Closes the dialogue box.
     * @param event
     */
    @FXML
    void okButtonPress(ActionEvent event) {
        s.close();
    }

}
