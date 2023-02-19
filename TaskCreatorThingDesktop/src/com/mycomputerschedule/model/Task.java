package com.mycomputerschedule.model;


import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Callback;
import javafx.beans.property.StringProperty;

/**
 * The model of what a Task is. It has a name, time, and a brief note.
 */
public class Task {

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getHour() {
        return hour.get();
    }

    public IntegerProperty hourProperty() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour.set(hour);
    }

    public String getMin() {
        return min.get();
    }

    public StringProperty minProperty() {
        return min;
    }

    public void setMin(String min) {
        this.min.set(min);
    }

    public String getAmpm() {
        return ampm.get();
    }

    public StringProperty ampmProperty() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm.set(ampm);
    }

    public String getDesc() {
        return desc.get();
    }

    public StringProperty descProperty() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc.set(desc);
    }

    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final IntegerProperty hour = new SimpleIntegerProperty(this, "hour", 0);
    private final StringProperty min = new SimpleStringProperty(this, "min", "0");
    private final StringProperty ampm = new SimpleStringProperty(this, "ampm", "AM");
    private final StringProperty desc = new SimpleStringProperty(this, "desc", "None");

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private boolean visible = true;

    public Task() {

    }

    /**
     * Creates a task from the file input
     *
     * @param str Input String
     * @return
     */
    public static Task fromFile(String str) {

        String input = str;
        Task t = new Task();
        String[] spl = input.split("~");
        //Sets Visible boolean
        t.setVisible(Boolean.parseBoolean(spl[0]));
        //Sets name
        t.setName(spl[1]);
        //Sets hour
        t.setHour(Integer.parseInt(spl[2]));
        //Sets min
        t.setMin(spl[3]);
        //Sets if AM or PM
        t.setAmpm(spl[4]);
        //Sets desc
        if (spl.length == 5) {
            t.setDesc("No notes.");
            return t;
        }
        t.setDesc(spl[5]);
        return t;
    }

    /**
     * This returns a string designed to be written to a file. The format is:
     * visible~name~hour~min~ampm~desc
     *
     * @return
     */
    public String fileString() {

        return visible + "~" + name.get() + "~" + hour.get() + "~" + min.get() + "~" + ampm.get() + "~" + desc.get();

    }

    @Override
    public String toString() {
        return hour.get() + ":" + min.get() + " " + ampm.get() + " - " + name.get();
    }

    public static Callback<Task, Observable[]> extractor = p -> new Observable[]
            {p.nameProperty(), p.hourProperty(), p.minProperty()};


}
