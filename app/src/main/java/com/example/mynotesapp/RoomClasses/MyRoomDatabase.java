package com.example.mynotesapp.RoomClasses;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version = 1,exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    private static MyRoomDatabase database;
    private static String DATABASE_NAME = "notes_db";
    public static synchronized MyRoomDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),
                    MyRoomDatabase.class,DATABASE_NAME).
                    allowMainThreadQueries().
                    fallbackToDestructiveMigration().
                    build();
        }
        return database;
    }
}
