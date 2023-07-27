package com.example.mynotesapp.Other;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mynotesapp.RoomClasses.Note;

public interface OnClickListner {
    public void onClick(Note note);
    public void onLogClick(Note note, ConstraintLayout note_container);
}
