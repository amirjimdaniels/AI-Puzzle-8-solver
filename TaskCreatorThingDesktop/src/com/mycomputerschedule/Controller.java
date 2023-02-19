package com.mycomputerschedule;

import com.mycomputerschedule.model.Task;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javafx.event.ActionEvent;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.util.Duration;

/**
 * Our main controller for our UI. It handles the list with all of the tasks.
 */
public class Controller {

    ObservableList<Task> obsTask = FXCollections.observableArrayList(Task.extractor);

    private EditController editCon;

    @FXML
    private Button newTaskButton;

    @FXML
    private Button editButton;

    @FXML
    private Button completeButton;

    @FXML
    private ListView<Task> taskView;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem newTaskItem;

    @FXML
    private Menu editMenu;

    @FXML
    private MenuItem editItem;

    @FXML
    private MenuItem deleteItem;

    @FXML
    private Label dateLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label timeLabel;


    /**
     * Sets the binds on our edit and complete button so that a task has to be selected.
     */
    @FXML
    public void initialize() {


        editButton.disableProperty().bind(taskView.getSelectionModel().selectedItemProperty().isNull());
        completeButton.disableProperty().bind(taskView.getSelectionModel().selectedItemProperty().isNull());
        obsTask = FileOperations.readFile();
        setTimeDate();

        /*
        Creates a timeline so that the date and time are up to date
         */
        Timeline updateTimeDate = new Timeline(
                new KeyFrame(Duration.seconds(5), e -> setTimeDate()
                ));
        updateTimeDate.setCycleCount(Timeline.INDEFINITE);
        updateTimeDate.play();

        SortedList<Task> sList = new SortedList<>(obsTask);

        /*
        Sorts the list of tasks
         */
        sList.setComparator((p1, p2) -> {
            int res = p1.getAmpm().compareToIgnoreCase(p2.getAmpm());
            if (res == 0) {
                res = Integer.compare(p1.getHour(), p2.getHour());

                /*
                Because the 12 hour acts like a zero, we have to do this
                 */
                if (p1.getHour() == 12 && p2.getHour() != 12) {
                    res = -1;
                } else if (p2.getHour() == 12 && p1.getHour() != 12) {
                    res = 1;
                }
            }
            if (res == 0) {
                res = p1.getMin().compareToIgnoreCase(p2.getMin());
            }

            return res;

        });

        taskView.setItems(sList);


    }

    /**
     * Creates an edit view popup so we can make and edit tasks.
     *
     * @param event When New Task Button is Clicked
     */
    @FXML
    void newTaskButtonAction(ActionEvent event) {

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("editTaskView.fxml"));
            Parent p = load.load();
            editCon = load.getController();

            Stage stage = new Stage();
            editCon.setMainControl(this);
            stage.setScene(new Scene(p));
            stage.setResizable(false);
            stage.setTitle("Edit View");
            stage.setAlwaysOnTop(true);
            editCon.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * Removes the Item from the list.
     *
     * @param event When complete button is clicked.
     */

    @FXML
    void completeButtonAction(ActionEvent event) {


        Task remTask = taskView.getSelectionModel().getSelectedItem();

        obsTask.remove(remTask);

        FileOperations.writeToFile(obsTask);


    }


    /**
     * Makes a new edit view, and has it populate the already created fields.
     *
     * @param e When edit button is clicked.
     */
    @FXML
    void editButtonAction(ActionEvent e) {
        Task sel = taskView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("editTaskView.fxml"));
            Parent p = load.load();
            editCon = load.getController();

            Stage stage = new Stage();
            editCon.setMainControl(this);
            stage.setScene(new Scene(p));
            stage.setResizable(false);
            stage.setTitle("Edit View");
            stage.setAlwaysOnTop(true);
            editCon.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            editCon.populateFields(sel);
            stage.show();

        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }

    /**
     * Sets the date and time from the system clock.
     */
    private void setTimeDate() {

        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().toString();

        System.out.println(time);

        String[] date2 = date.split("-");
        String month = "Invalid";
        int realDate = Integer.parseInt(date2[2]);

        dateLabel.setText(realDate + "");


        switch (Integer.parseInt(date2[1])) {
            case 1 -> month = "January";
            case 2 -> month = "February";
            case 3 -> month = "March";
            case 4 -> month = "April";
            case 5 -> month = "May";
            case 6 -> month = "June";
            case 7 -> month = "July";
            case 8 -> month = "August";
            case 9 -> month = "September";
            case 10 -> month = "October";
            case 11 -> month = "November";
            case 12 -> month = "December";
        }

        monthLabel.setText(month);

        String[] time2 = time.split(":");

        String amPm = "AM";
        int realHours = Integer.parseInt(time2[0]);

        if (Integer.parseInt(time2[0]) >= 12) {
            amPm = "PM";
        }

        if (realHours > 12) {
            realHours -= 12;
        }

        timeLabel.setText(realHours + ":" + time2[1] + " " + amPm);

    }


}
