package com.anda.notes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import data.Note;
import models.DatabaseHandler;


public class MainActivity extends ActionBarActivity {

    private EditText title;
    private EditText content;
    private Button button;
    private DatabaseHandler databaseHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHandler = new DatabaseHandler(MainActivity.this);
        title = (EditText)findViewById(R.id.titleEditText);
        content = (EditText)findViewById(R.id.noteEditText);
        button = (Button)findViewById(R.id.saveButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date d = new Date();
                CharSequence s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
                Note note = new Note();
                note.set_title(title.getText().toString().trim());
                note.set_content(content.getText().toString().trim());
                note.set_date(s.toString());

                databaseHandler.insert(note);
                databaseHandler.close();
                title.setText("");
                content.setText("");

                Intent intent = new Intent(MainActivity.this, DisplayNotes.class);
                startActivity(intent);
            }
        });
    }
}
