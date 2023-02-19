package com.mycomputerschedule;

import com.mycomputerschedule.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Scanner;

/**
 * This class will manage our reads and writes to files.
 */
public class FileOperations {

    /**
     * Makes sure a file exists, then reads the file into an observable list.
     *
     * @return An observable list of all read in tasks.
     */
    public static ObservableList<Task> readFile() {
        ObservableList<Task> retArray = FXCollections.observableArrayList(Task.extractor);
        createFile();
        try {
            File taskFile = new File("TaskList.txt");
            Scanner reader = new Scanner(taskFile);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                retArray.add(Task.fromFile(data));
            }
            reader.close();
            System.out.println("File read successful.");


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File was Not Found.");
        }

        return retArray;


    }


    /**
     * Writes the whole list to the file
     *
     * @param list List of tasks to be written in.
     */
    public static void writeToFile(ObservableList<Task> list) {
        createFile();
        PrintWriter writer;
        try {
            writer = new PrintWriter(new FileWriter("TaskList.txt"));
            for (Task task : list) {
                System.out.println(task.toString());
                writer.println(task.fileString());
            }

            System.out.println("TaskList File Updated");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**
     * Creates a the file. If it already exists, it doesn't have to make a new one.
     */
    private static void createFile() {
        try {

            File infoFile = new File("TaskList.txt");
            if (infoFile.createNewFile()) {
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