package com.shiva.newnoteapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shiva.newnoteapp.entities.Notes;

import java.util.List;


@Dao
public interface NotesDao {

    @Insert
    void AddNote (Notes note);

    @Query("SELECT * FROM Notes ORDER BY `key` DESC")
    List<Notes> GetAllNotes();

    @Query("DELETE FROM Notes WHERE `key` = :id")
    void DeleteNote(int id);

}
