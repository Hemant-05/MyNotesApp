package com.example.mynotesapp.Activitys;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynotesapp.Other.NotesAdapter;
import com.example.mynotesapp.Other.OnClickListner;
import com.example.mynotesapp.R;
import com.example.mynotesapp.RoomClasses.MyRoomDatabase;
import com.example.mynotesapp.RoomClasses.Note;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements OnClickListner, PopupMenu.OnMenuItemClickListener {
    RecyclerView showing_note_rv;
    SearchView search_notes;
    TextView my_notes_text,no_notes_text;
    NotesAdapter adapter;
    ImageView add_btn;
    List<Note> allNotes;
    MyRoomDatabase database;
    Note selectedNote;
    SwipeRefreshLayout swipedowntoregresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        setId();
        no_notes_text.setVisibility(View.GONE);

        database = MyRoomDatabase.getInstance(getApplicationContext());
        allNotes = database.noteDao().getAllNotes();

        settingRV(allNotes);

        search_notes.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                filter(text);
                return true;
            }
            private void filter(String text) {
                List<Note> filteredList = new ArrayList<>();
                for(Note singleNote : allNotes){
                    if(singleNote.note_title.toLowerCase().contains(text.toLowerCase())
                    || singleNote.note_content.toLowerCase().contains(text.toLowerCase())){
                        filteredList.add(singleNote);
                    }
                }
                adapter.setFilteredList(filteredList);
            }
        });
        swipedowntoregresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipedowntoregresh.setRefreshing(false);
            }
        });

        add_btn.setOnClickListener(v->{
            Intent intent = new Intent(HomeActivity.this,NoteTakerActivity.class);
            startActivityForResult(intent,101);
            refreshRV();
        });
    }

    private void setId() {
        showing_note_rv = findViewById(R.id.showing_note_rv);
        add_btn = findViewById(R.id.add_btn);
        search_notes = findViewById(R.id.search_notes);
        my_notes_text = findViewById(R.id.my_notes_text);
        no_notes_text = findViewById(R.id.no_notes_text);
        swipedowntoregresh = findViewById(R.id.swipdowntorefresh);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Note note = (Note) data.getSerializableExtra("note");
                database.noteDao().insertNote(note);
              refreshRV();
            }
        }else if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK){
                Note note = (Note) data.getSerializableExtra("note");
                database.noteDao().updateNote(note.id,note.note_title,note.note_content, note.note_date);
               refreshRV();
            }
        }
    }
    public void refreshRV(){
        allNotes.clear();
        allNotes.addAll(database.noteDao().getAllNotes());
        adapter.notifyDataSetChanged();
    }
    private void settingRV(List<Note> allNotes) {
        showing_note_rv.setHasFixedSize(true);
        showing_note_rv.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        adapter = new NotesAdapter(getApplicationContext(),allNotes,this);
        showing_note_rv.setAdapter(adapter);
    }
    @Override
    public void onClick(Note note) {
        Intent intent = new Intent(HomeActivity.this,NoteTakerActivity.class);
        intent.putExtra("old_note",note);
        startActivityForResult(intent,102);
    }

    @Override
    public void onLogClick(Note note, ConstraintLayout note_container) {
        selectedNote = new Note();
        selectedNote = note;
        ShowPopup(note_container);
    }

    private void ShowPopup(ConstraintLayout note_container) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),note_container);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.pin) {
            if (selectedNote.note_pin_state) {
                database.noteDao().pin_unpin(selectedNote.id, false);
                Toast.makeText(this, "Unpinned !", Toast.LENGTH_SHORT).show();
            } else {
                database.noteDao().pin_unpin(selectedNote.id, true);
                Toast.makeText(this, "Pinned !", Toast.LENGTH_SHORT).show();
            }
            refreshRV();
            return true;
        } else if (itemId == R.id.delete) {
            database.noteDao().deleteNote(selectedNote);
            Toast.makeText(this, selectedNote.note_title+" Deleted Successfully...", Toast.LENGTH_SHORT).show();
            refreshRV();
            return true;
        }
        return false;
    }
}