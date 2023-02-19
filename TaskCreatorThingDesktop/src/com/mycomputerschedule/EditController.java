package com.mycomputerschedule;

import com.mycomputerschedule.model.Task;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This controller will control the edit/ create a new task window.
 */
public class EditController {

    private Stage stage;

    private Controller mainControl;

    private Task editTask;

    @FXML
    private TextField nameBox;

    @FXML
    private TextField descBox;

    @FXML
    private TextField hoursBox;

    @FXML
    private TextField minBox;

    @FXML
    private ComboBox<String> apBox;

    @FXML
    private Button retButton;

    @FXML
    private Button saveButton;

    /**
     * Initializes the edit window.
     */
    @FXML
    public void initialize() {

        String[] amOrPm = {"AM", "PM"};

        apBox.setItems(FXCollections.observableArrayList(amOrPm));

        saveButton.disableProperty().bind(Bindings.isEmpty(nameBox.textProperty())
                .or(Bindings.isEmpty(hoursBox.textProperty()))
                .or(Bindings.isEmpty(minBox.textProperty()))
                .or(Bindings.isNull(apBox.valueProperty())));

    }

    /**
     * Returns to the main window
     *
     * @param event When back button is clicked.
     */
    @FXML
    void retButtonAction(ActionEvent event) {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMainControl(Controller ctrl) {
        this.mainControl = ctrl;
    }

    /**
     * Will save the inputs for a new task, or replace an updated older task.
     *
     * @param event When save button is clicked.
     */
    @FXML
    void saveButtonAction(ActionEvent event) {

        if (!validFields()) {


            /*
            Create a popup describing problem
             */
            try {

                FXMLLoader load = new FXMLLoader(getClass().getResource("notValid.fxml"));
                Parent p = load.load();
                NotValidController v = load.getController();
                Stage stage = new Stage();
                stage.setScene(new Scene(p));
                stage.setResizable(false);
                stage.setTitle("Invalid Fields");
                v.setS(stage);
                stage.setAlwaysOnTop(true);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("Inputs not valid.");
            return;
        }
        Task t = new Task();
        t.setName(nameBox.getText());
        t.setHour(Integer.parseInt(hoursBox.getText()));
        String s = "0";

        //number is between 0 and 9 with out the leading zero
        if (minBox.getText().length() == 1) {
            s = s.concat(minBox.getText());
            t.setMin(s);
        } else {
            t.setMin(minBox.getText());
        }

        t.setAmpm(apBox.getValue());
        t.setDesc(descBox.getText());

        //bad implementation, but it works. mostly
        mainControl.obsTask.add(t);

        if (editTask != null) {
            mainControl.obsTask.remove(editTask);
        }

        FileOperations.writeToFile(mainControl.obsTask);

        stage.close();
    }

    /**
     * Checks to see if all of the user input fields are valid
     *
     * @return Returns true if all of the fields are valid.
     */
    private boolean validFields() {

        //were using ~ in the files to seperate the parts of the tasks. FileInput/output didnt play nicely
        // with properties...
        if ((!nameBox.getText().contains("~")) && (nameBox.getText() != null)) {
            try {
                int n = Integer.parseInt(hoursBox.getText());

                if (n <= 0 || n > 13) {
                    return false;
                }

            } catch (NumberFormatException e) {
                return false;
            }

            try {
                int p = Integer.parseInt(minBox.getText());

                if (p < 0 || p > 59) {
                    return false;
                }

            } catch (NumberFormatException e) {
                return false;
            }
            return apBox.getValue() != null;

        }
        return false;
    }

    /**
     * Populates the fields when we are editing a task that was already created.
     *
     * @param t
     */
    public void populateFields(Task t) {
        this.editTask = t;
        nameBox.setText(t.getName());
        hoursBox.setText(Integer.toString(t.getHour()));
        minBox.setText(t.getMin());
        apBox.setValue(t.getAmpm());
        descBox.setText(t.getDesc());

    }


}