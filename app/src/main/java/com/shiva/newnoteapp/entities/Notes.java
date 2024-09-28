package com.shiva.newnoteapp.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Notes  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    int key;

    @ColumnInfo(name = "NoteTitle")
    String NoteTitle;

    @ColumnInfo(name = "NoteBody")
    String NoteBody;

    @ColumnInfo(name = "CreatedTime")
    String CreatedTime;

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getNoteTitle() {
        return NoteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        NoteTitle = noteTitle;
    }

    public String getNoteBody() {
        return NoteBody;
    }

    public void setNoteBody(String noteBody) {
        NoteBody = noteBody;
    }

    public Notes(String noteTitle, String noteBody, String createdTime) {
        NoteTitle = noteTitle;
        NoteBody = noteBody;
        CreatedTime = createdTime;
    }

    public Notes() {
    }
}
