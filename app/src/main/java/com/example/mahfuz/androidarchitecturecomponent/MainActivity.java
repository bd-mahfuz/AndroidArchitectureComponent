package com.example.mahfuz.androidarchitecturecomponent;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mahfuz.androidarchitecturecomponent.model.Note;
import com.example.mahfuz.androidarchitecturecomponent.viewmodel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView mNoteRv;

    private List<Note> notes;

    private FloatingActionButton mAddButton;
    
    public static final int ADD_NOTE_REQUEST_CODE = 1;
    public static final int EDIT_NOTE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteRv = findViewById(R.id.note_rv);
        mAddButton = findViewById(R.id.add_note_bt);
        mNoteRv.setLayoutManager(new LinearLayoutManager(this));

        final NoteAdapter adapter = new NoteAdapter();
        mNoteRv.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {

                adapter.setNotes(notes);

                Toast.makeText(MainActivity.this, notes.size()+"", Toast.LENGTH_SHORT).show();
            }
        });

        /*NoteDatabase noteDatabase = NoteDatabase.getInstance(this);
        noteDatabase.noteDao().insert(new Note("Title 1", "Description 1", 1));

        notes = noteDatabase.noteDao().getNotes();

        Toast.makeText(MainActivity.this, notes.size()+"", Toast.LENGTH_SHORT).show();
*/

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.delete(adapter.noteAtPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note is deleted", Toast.LENGTH_SHORT).show();                
            }
        }).attachToRecyclerView(mNoteRv);



        adapter.setOnclickListener(new NoteAdapter.OnClickListener() {
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.ID_EXTRA, note.getId());
                intent.putExtra(AddEditNoteActivity.TITLE_EXTRA, note.getTitle());
                intent.putExtra(AddEditNoteActivity.DESCRIPTION_EXTRA, note.getDescription());
                intent.putExtra(AddEditNoteActivity.PRIORITY_EXTRA, note.getPriority());

                startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, AddEditNoteActivity.class));
                
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST_CODE);
                
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == ADD_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            
            String title = data.getStringExtra(AddEditNoteActivity.TITLE_EXTRA);
            String description = data.getStringExtra(AddEditNoteActivity.DESCRIPTION_EXTRA);
            int priority = Integer.valueOf(data.getStringExtra(AddEditNoteActivity.PRIORITY_EXTRA));
            
            Note note = new Note(title, description, priority);
            
            noteViewModel.insert(note);

            Toast.makeText(this, "New Note is created.", Toast.LENGTH_SHORT).show();
            
        } else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {

            int id = data.getIntExtra(AddEditNoteActivity.ID_EXTRA, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't update", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditNoteActivity.TITLE_EXTRA);
            String description = data.getStringExtra(AddEditNoteActivity.DESCRIPTION_EXTRA);
            int priority = Integer.valueOf(data.getStringExtra(AddEditNoteActivity.PRIORITY_EXTRA));

            Note note = new Note(title, description, priority);
            note.setId(id);

            noteViewModel.update(note);

            Toast.makeText(this, "Note is updated", Toast.LENGTH_SHORT).show();


        } else {

            Toast.makeText(this, "Note not Created", Toast.LENGTH_SHORT).show();
            
        }
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.delete_menu:
                noteViewModel.deleteAll();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
