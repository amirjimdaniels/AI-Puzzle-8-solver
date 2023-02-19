package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.stage.Stage;

public class EditController {

    private Stage stage;

    private Controller mainControl;

    private Task editTask;

    @FXML
    private TextField nameBox;

    @FXML
    private TextArea descBox;

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

    @FXML
    public void initialize(){

        String amOrPm[] = {"AM", "PM"};

        apBox.setItems(FXCollections.observableArrayList(amOrPm));

        BooleanBinding isValid = Bindings.isEmpty(nameBox.textProperty());

        saveButton.disableProperty().bind(Bindings.isEmpty(nameBox.textProperty())
            .or(Bindings.isEmpty(hoursBox.textProperty()))
                .or(Bindings.isEmpty(minBox.textProperty()))
                .or(Bindings.isNull(apBox.valueProperty())));

    }

    @FXML
    void retButtonAction(ActionEvent event) {
        stage.close();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void setMainControl(Controller ctrl){
        this.mainControl = ctrl;
    }

    @FXML
    void saveButtonAction(ActionEvent event) {

        if (validFields() == false){
            //TODO create popup
            System.out.println("Inputs not valid.");
            return;
        }
        Task t = new Task();
        t.setName(nameBox.getText());
        t.setHour(Integer.parseInt(hoursBox.getText()));
        String s = "0";
        //number is between 0 and 9 with out the leading zero
        if(minBox.getText().length() == 1){
            s = s.concat(minBox.getText());
            t.setMin(s);
        } else{
            t.setMin(minBox.getText());
        }

        t.setAmpm(apBox.getValue());
        t.setDesc(descBox.getText());

        mainControl.obsTask.add(t);

        if(editTask != null){
            mainControl.obsTask.remove(editTask);
        }

        FileOperations.writeToFile(mainControl.obsTask);

//        obsTask.add(t);
//        for(int i = 0; i < obsTask.size(); i++){
//            System.out.println(obsTask.get(i).toString());
//
//        }
//        FileOperations.writeToFile(obsTask);
//        //TODO Write to file
//
//        changeScene("sample.fxml");
        stage.close();
        return;
    }

    private boolean validFields(){
        if((nameBox.getText().contains("~") == false) && (nameBox.getText() != null)){
            try {
                int n = Integer.parseInt(hoursBox.getText());

                if(n < 0 || n > 13){
                    return false;
                }

            } catch (NumberFormatException e){
                return false;
            }

            try {
                int p = Integer.parseInt(minBox.getText());

                if(p < 0 || p > 59){
                    return false;
                }

            } catch (NumberFormatException e){
                return false;
            }
            if(apBox.getValue() != null){
                return true;
            }

        }
        return false;
    }

    public void populateFields(Task t){
        this.editTask = t;
        nameBox.setText(t.getName());
        hoursBox.setText(Integer.toString(t.getHour()));
        minBox.setText(t.getMin());
        apBox.setValue(t.getAmpm());
        descBox.setText(t.getDesc());

    }






}