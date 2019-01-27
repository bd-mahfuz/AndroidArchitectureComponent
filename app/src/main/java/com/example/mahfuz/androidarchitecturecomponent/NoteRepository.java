package com.example.mahfuz.androidarchitecturecomponent;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mahfuz.androidarchitecturecomponent.dao.NoteDao;
import com.example.mahfuz.androidarchitecturecomponent.database.NoteDatabase;
import com.example.mahfuz.androidarchitecturecomponent.model.Note;

import java.util.List;

public class NoteRepository {

    private LiveData<List<Note>> notes;
    private NoteDao noteDao;

    public NoteRepository(Application application) {

        NoteDatabase noteDatabase = NoteDatabase.getInstance(application);
        noteDao = noteDatabase.noteDao();
        this.notes = noteDao.getAllNotes();

    }


    public void addNotes(Note note) {
        new AddNoteAsyncTask(noteDao).execute(note);

    }

    public void updateNotes(Note note) {

        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteNote(Note note) {

        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {

        new DeleteNoteAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }


    private static class AddNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        public AddNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }


    public static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;
        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;
        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    public static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;
        private DeleteAllNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }
}
