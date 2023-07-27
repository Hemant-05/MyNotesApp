package com.example.mynotesapp.Other;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynotesapp.R;
import com.example.mynotesapp.RoomClasses.Note;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    Context context;
    List<Note> noteList;
    OnClickListner onClickListner;
    public NotesAdapter(Context context, List<Note> noteList,OnClickListner onClickListner) {
        this.context = context;
        this.noteList = noteList;
        this.onClickListner = onClickListner;
    }

    @NonNull
    @Override
    public NotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_note_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.MyViewHolder holder, int position) {
        Note note = noteList.get(position);

        holder.note_show_title.setText(note.note_title);

        holder.note_show_content.setText(note.note_content);

        holder.note_show_date.setText(note.note_date);
        holder.note_show_date.setSelected(true);

        holder.note_container.setOnClickListener(view -> onClickListner.onClick(note));

        holder.note_container.setOnLongClickListener(view -> {
            onClickListner.onLogClick(note,holder.note_container);
            return true;
        });

        if(note.note_pin_state){
            holder.note_show_pin.setImageResource(R.drawable.pin);
        }else
        {
            holder.note_show_pin.setImageResource(R.drawable.unpin);
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setFilteredList(List<Note> filteredList) {
        noteList = filteredList;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView note_show_title,note_show_content,note_show_date;
        ImageView note_show_pin;
        ConstraintLayout note_container;

        public MyViewHolder(@NonNull View view) {
            super(view);
            note_show_content = view.findViewById(R.id.note_show_content);
            note_show_date = view.findViewById(R.id.note_show_time);
            note_show_title = view.findViewById(R.id.note_show_title);
            note_show_pin = view.findViewById(R.id.note_show_pin);
            note_container = view.findViewById(R.id.note_container);
        }
    }
}
