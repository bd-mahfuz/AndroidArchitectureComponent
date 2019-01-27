package com.example.mahfuz.androidarchitecturecomponent.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mahfuz.androidarchitecturecomponent.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("delete from note")
    void deleteAll();

    @Query("select * from note order by priority desc")
    LiveData<List<Note>> getAllNotes();

    @Query("select * from note order by priority desc")
    List<Note> getNotes();



}
