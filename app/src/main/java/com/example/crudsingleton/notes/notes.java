package com.example.crudsingleton.notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class notes {

    private int id;
    private String txt;
    private String dateTime;

    public notes(int id, String txt) {
        this.id = id;
        this.txt = txt;
        this.dateTime = new SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault()).format(new Date());
    }

    public notes(int id, String txt, String dateTime) {
        this.id = id;
        this.txt = txt;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
