package com.example.mynotesapp.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynotesapp.R;
import com.example.mynotesapp.RoomClasses.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteTakerActivity extends AppCompatActivity {

    ImageView take_note_pin_state, save_note;
    EditText take_note_title, take_note_content;
    Note note;
    Button save_btn;
    boolean isOldNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taker);
        getSupportActionBar().hide();
        initId();
        note = new Note();
        try{
            note = (Note) getIntent().getSerializableExtra("old_note");
            take_note_content.setText(note.note_content);
            take_note_title.setText(note.note_title);
            if(note.note_pin_state){
                take_note_pin_state.setImageResource(R.drawable.pin);
            }else{
                take_note_pin_state.setImageResource(R.drawable.unpin);
            }
            isOldNote = true;

        }
        catch (Exception e){
            e.printStackTrace();
        }

        save_note.setOnClickListener(v -> {
            saveNoteFun();
        });
    }

    void initId() {
        take_note_content = findViewById(R.id.take_note_content);
        take_note_title = findViewById(R.id.take_note_title);
        take_note_pin_state = findViewById(R.id.take_note_pin_state);
        save_note = findViewById(R.id.save_note);
        save_btn = findViewById(R.id.save_btn);
    }

    public void saveNoteFun() {
        String title = take_note_title.getText().toString();
        String content = take_note_content.getText().toString();

        if (content.isEmpty()) {
            Toast.makeText(this, "Enter Something !", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyy, HH:mm, a");
        Date date = new Date();
        if(!isOldNote){
            note = new Note();
        }
        note.note_title = title;
        note.note_content = content;
        note.note_date = formatter.format(date);

        Intent intent = new Intent();
        intent.putExtra("note", note);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
    public void save_note_btn(View view) {
        saveNoteFun();
    }

    public void back_to_home(View view) {
        onBackPressed();
    }
}