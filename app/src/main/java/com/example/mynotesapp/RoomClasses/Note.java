package com.example.mynotesapp.RoomClasses;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes_table")
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String note_title;

    @ColumnInfo(name = "content")
    public String note_content;

    @ColumnInfo(name = "date")
    public String note_date;

    @ColumnInfo(name = "pin_state")
    public boolean note_pin_state;
}
