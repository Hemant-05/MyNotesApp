package com.example.mynotesapp.RoomClasses;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("select * from notes_table order by id desc")
    public List<Note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertNote(Note note);

    @Query("update notes_table set title = :title, content = :content ,date = :date where id =:id ")
    public void updateNote(int id, String title, String content,String date);

    @Delete
    public void deleteNote(Note note);

    @Query("update notes_table set pin_state = :pin where id = :id")
    public void pin_unpin(int id,boolean pin);
}
