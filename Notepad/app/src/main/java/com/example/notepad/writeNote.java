package com.example.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import org.w3c.dom.Text;

import java.io.OutputStreamWriter;

public class writeNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EditText title = findViewById(R.id.title);
        EditText content = findViewById(R.id.content);
        final NoteItem note;
        FloatingActionButton fab = findViewById(R.id.fab);


        note = (NoteItem) getIntent().getSerializableExtra("newNote");
        title.setText(note.title);
        content.setText(note.content);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(writeNote.this, MainActivity.class);
                startActivity(intent);
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.title = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    OutputStreamWriter out =
                            new OutputStreamWriter(openFileOutput(note.fileName + "Title", 0));
                    out.write(note.title);
                    out.close();

                } catch (Throwable t) {

                }

            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                note.content = charSequence.toString();


            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("DSJIFHDSAIUFHENAFIU");
                System.out.println(note.fileName);
                try {
                    OutputStreamWriter out =
                            new OutputStreamWriter(openFileOutput(note.fileName + "Content", 0));
                    out.write(note.content);
                    out.close();

                } catch (Throwable t) {

                }
            }
        });


    }

    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onStop(){
        super.onStop();


    }



}
