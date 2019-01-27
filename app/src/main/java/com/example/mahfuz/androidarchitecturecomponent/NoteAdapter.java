package com.example.mahfuz.androidarchitecturecomponent;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahfuz.androidarchitecturecomponent.model.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes = new ArrayList<>();

    private OnClickListener onClickListener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Note currentNote = notes.get(position);
        holder.priorityTv.setText(String.valueOf(currentNote.getPriority()));
        holder.descriptiontv.setText(currentNote.getDescription());
        holder.titleTv.setText(currentNote.getTitle());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note noteAtPosition(int position) {
        return notes.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv, descriptiontv, priorityTv;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTv = itemView.findViewById(R.id.title_tv);
            descriptiontv = itemView.findViewById(R.id.description_tv);
            priorityTv = itemView.findViewById(R.id.priority_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(notes.get(getAdapterPosition()));
                    }
                }
            });

        }
    }


    interface OnClickListener{
        void onClick(Note note);
    }

    public void setOnclickListener(OnClickListener onclickListener) {
        this.onClickListener = onclickListener;
    }
}
