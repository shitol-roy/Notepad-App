package com.shiva.newnoteapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.shiva.newnoteapp.dao.NotesDao;
import com.shiva.newnoteapp.entities.Notes;

@Database(entities = {Notes.class} , version = 1  )
public abstract class NotesDB extends RoomDatabase {

    private static NotesDB notedb ;

    public static synchronized NotesDB getDB(Context context){
        if (notedb == null){
            notedb = Room.databaseBuilder(context , NotesDB.class , "notesDB").build();
            return notedb;
        }
        return notedb;
    }

    public abstract NotesDao notesDao();

}
