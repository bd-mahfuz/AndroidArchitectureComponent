package com.example.mahfuz.androidarchitecturecomponent.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mahfuz.androidarchitecturecomponent.NoteRepository;
import com.example.mahfuz.androidarchitecturecomponent.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel{

    private NoteRepository noteRepository;
    private LiveData<List<Note>> notes;

    public NoteViewModel(Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        notes = noteRepository.getNotes();
    }

    public void insert(Note note) {
        noteRepository.addNotes(note);
    }

    public void update(Note note) {
        noteRepository.updateNotes(note);
    }

    public void delete(Note note) {
        noteRepository.deleteNote(note);
    }


    public void deleteAll() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }
}
