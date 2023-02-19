package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperations {

    //TODO Create a format for the Tasks
    public static ObservableList<Task> readFile (){
        ObservableList<Task> retArray = FXCollections.observableArrayList(Task.extractor);
        createFile();
        try {
            File taskFile = new File("TaskList.txt");
            Scanner reader = new Scanner(taskFile);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                retArray.add(Task.fromFile(data));
            }
            reader.close();
            System.out.println("File read successful.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File was Not Found.");
        }

        return retArray;



    }

    //TODO Files in Java suck
    public static void writeToFile (ObservableList<Task> list) {
        createFile();
       PrintWriter writer;
        try {
            writer = new PrintWriter(new FileWriter("TaskList.txt"));
            for (int i = 0; i < list.size(); i++){
                System.out.println(list.get(i).toString());
                writer.println(list.get(i).fileString());
            }


            System.out.println("TaskList File Updated");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void createFile(){
        try{

            File infoFile = new File("TaskList.txt");
            if(infoFile.createNewFile()){
                System.out.println("TaskList File was missing, making a new one");
            } else {
                System.out.println("TaskList File already exists");
            }

        } catch (IOException e) {
            System.out.println("Error in file creation");
            e.printStackTrace();
        }
    }
}