package com.microsoft.azuresample.sqlkeyvault.model;

/**
 * Created by vazvadsk on 2016-12-02.
 */
public class ToDo {
    private int id;
    private String note;
    private String mySecretNote;

    public ToDo(){

    }

    public ToDo(int id, String note, String mySecretNote){
        this.id = id;
        this.note = note;
        this.mySecretNote = mySecretNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMySecretNote() {
        return mySecretNote;
    }

    public void setMySecretNote(String mySecretNote) {
        this.mySecretNote = mySecretNote;
    }
}
