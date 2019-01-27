package com.example.mahfuz.androidarchitecturecomponent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.mahfuz.androidarchitecturecomponent.viewmodel.NoteViewModel;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String ID_EXTRA = "ID";
    public static final String TITLE_EXTRA = "Title";
    public static final String DESCRIPTION_EXTRA = "Description";
    public static final String PRIORITY_EXTRA = "Priority";

    private EditText mTitleEt, mDescriptionEt;
    private NumberPicker mNumberPicker;
    private Button saveNotebt;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        initView();

        //noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        Intent intent = getIntent();

        if (intent.hasExtra(ID_EXTRA)) {
            getSupportActionBar().setTitle("Edit Note");

            mTitleEt.setText(intent.getStringExtra(TITLE_EXTRA));
            mDescriptionEt.setText(intent.getStringExtra(DESCRIPTION_EXTRA));
            mNumberPicker.setValue(intent.getIntExtra(PRIORITY_EXTRA, 1));

        } else {
            getSupportActionBar().setTitle("Add Note");
        }




    }

    private void initView() {

        mTitleEt = findViewById(R.id.titleEt);
        mDescriptionEt = findViewById(R.id.descriptionEt);
        mNumberPicker= findViewById(R.id.time_picker);
        saveNotebt= findViewById(R.id.save_Note_bt);

    }


    @Override
    protected void onResume() {
        super.onResume();

        saveNotebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int priority = 0;
                String title = mTitleEt.getText().toString();
                String description = mDescriptionEt.getText().toString();
                priority = mNumberPicker.getValue();

                if (title.trim().isEmpty() && description.trim().isEmpty()) {
                    Toast.makeText(AddEditNoteActivity.this, "Title and description should not be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra(TITLE_EXTRA, title);
                intent.putExtra(DESCRIPTION_EXTRA, description);
                intent.putExtra(PRIORITY_EXTRA, priority+"");


                int id = getIntent().getIntExtra(ID_EXTRA, -1);
                if (id != -1) {
                    intent.putExtra(ID_EXTRA, id);
                }

                setResult(RESULT_OK, intent);
                finish();

                /*Note note = new Note(title, description, priority);

                noteViewModel.insert(note);

                Toast.makeText(AddEditNoteActivity.this, "Note inserted", Toast.LENGTH_SHORT).show();
                finish();*/
            }
        });

    }
}
