package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;



import javafx.event.ActionEvent;


import java.io.IOException;
import javafx.fxml.FXML;
import javafx.util.Duration;

import java.net.URL;

import java.time.LocalTime;
import java.util.ResourceBundle;

public class Controller {

    ObservableList<Task> obsTask  = FXCollections.observableArrayList(Task.extractor);

    private EditController editCon;

    @FXML private Button newTaskButton;

    @FXML private Button editButton;// = new Button();

    @FXML private Button completeButton;// = new Button();

    @FXML private ListView<Task> taskView;// = new ListView<Task>();

    @FXML private MenuBar menuBar;

    @FXML private MenuItem newTaskItem;// = new MenuItem();

    @FXML private Menu editMenu;

    @FXML private MenuItem editItem;// = new MenuItem();

    @FXML private MenuItem deleteItem;// = new MenuItem();

    @FXML private Label dateLabel;

    @FXML private Label monthLabel;

    @FXML private Label timeLabel;




    @FXML
    public void initialize() {


        editButton.disableProperty().bind(taskView.getSelectionModel().selectedItemProperty().isNull());
        completeButton.disableProperty().bind(taskView.getSelectionModel().selectedItemProperty().isNull());
        obsTask = FileOperations.readFile();
        setTimeDate();

        Timeline updateTimeDate = new Timeline(
                new KeyFrame(Duration.seconds(5),  e -> {
                    setTimeDate();
                    System.out.println("time works");
        }
        ));
        updateTimeDate.setCycleCount(Timeline.INDEFINITE);
        updateTimeDate.play();

        SortedList<Task> sList = new SortedList<Task>(obsTask);

        sList.setComparator((p1,p2) -> {
            int res = p1.getAmpm().compareToIgnoreCase(p2.getAmpm());
            if(res == 0){
                res = Integer.toString(p1.getHour()).compareToIgnoreCase(Integer.toString(p2.getHour()));
            } if (res ==0){
                res = p1.getMin().compareToIgnoreCase(p2.getMin());
            }

            return res;

        });

        taskView.setItems(sList);


    }

    @FXML void newTaskButtonAction (ActionEvent event) {

        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("editTaskView.fxml"));
            Parent p = load.load();
            editCon = load.getController();

            Stage stage = new Stage();
            editCon.setMainControl(this);
            stage.setScene(new Scene(p));
            stage.setResizable(false);
            stage.setTitle("Task Master - Edit View");
            stage.setAlwaysOnTop(true);
            editCon.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    @FXML
    void completeButtonAction(ActionEvent event) {

        //TODO Create confimation prompt

        Task remTask = taskView.getSelectionModel().getSelectedItem();

        obsTask.remove(remTask);

        FileOperations.writeToFile(obsTask);


    }


    @FXML void editButtonAction(ActionEvent e) {
        Task sel = taskView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader load = new FXMLLoader(getClass().getResource("editTaskView.fxml"));
            Parent p = load.load();
            editCon = load.getController();

            Stage stage = new Stage();
            editCon.setMainControl(this);
            stage.setScene(new Scene(p));
            stage.setResizable(false);
            stage.setTitle("Task Master - Edit View");
            stage.setAlwaysOnTop(true);
            editCon.setStage(stage);
            stage.initModality(Modality.APPLICATION_MODAL);
            editCon.populateFields(sel);
            stage.show();

        } catch (IOException exception) {
            exception.printStackTrace();
        }



    }

    private void setTimeDate(){

        String date = java.time.LocalDate.now().toString();
        String time = java.time.LocalTime.now().toString();

        System.out.println(date);
        System.out.println(time);

        String date2[] = date.split("-");
        String month = "Invalid";
        int realDate = Integer.parseInt(date2[2]);

        dateLabel.setText(realDate + "");


        switch (Integer.parseInt(date2[1])) {
            case 1:  month = "January";
                break;
            case 2:  month = "February";
                break;
            case 3:  month = "March";
                break;
            case 4:  month = "April";
                break;
            case 5:  month = "May";
                break;
            case 6:  month= "June";
                break;
            case 7:  month = "July";
                break;
            case 8:  month = "August";
                break;
            case 9:  month = "September";
                break;
            case 10: month = "October";
                break;
            case 11: month = "November";
                break;
            case 12: month = "December";
                break;

        }

        monthLabel.setText(month);

        String time2[] = time.split(":");

        String amPm = "AM";
        int realHours = Integer.parseInt(time2[0]);

        if (Integer.parseInt(time2[0]) >= 12 ){
            amPm = "PM";
        }

        if(realHours > 12){
            realHours -= 12;
        }

        timeLabel.setText(realHours + ":" + time2[1] + " " + amPm);

    }





}
