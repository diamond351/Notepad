package com.example.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.*;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<NoteItem> testDataset;
    int curNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testDataset = new ArrayList<NoteItem>();
        FloatingActionButton fab = findViewById(R.id.fab);
        //File fdelete = new File (files[0].g)

       /* try (FileOutputStream fos = getApplicationContext().openFileOutput(filename, getApplicationContext().MODE_PRIVATE)) {
            fos.write(fileContents.getBytes());
        } catch (Exception e) {

        }*/
       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        try {
            OutputStreamWriter out =
                    new OutputStreamWriter(openFileOutput("note2Title", 0));
            out.write("Titl2");
            out.close();

        } catch (Throwable t) {

        }

        try {
            OutputStreamWriter out =
                    new OutputStreamWriter(openFileOutput("note2Content", 0));
            out.write("Content2");
            out.close();

        } catch (Throwable t) {

        }

        SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (sharedPref.getInt("curNumber", -1) == -1) {
            curNum = 1;
            editor.putInt("curNumber", 1);
            editor.commit();
        } else {
            curNum = sharedPref.getInt("curNumber", -1);
        }

        File fileDirec = getFilesDir();
        File[] files = fileDirec.listFiles();
        int size = files.length;
        String fileName;
        String title;
        String content;
        for (int i = 1; i < curNum; i++) {
            File file = getBaseContext().getFileStreamPath("note" + i + "Title");
            File file2 = getBaseContext().getFileStreamPath("note" + i + "Content");
            if (file.exists()) {
                if (file.length() == 0 && file2.length() == 0) {
                    file.delete();
                    file2.delete();
                } else {
                    fileName = "note" + i + "Title";
                    title = openFile("note" + i + "Title");
                    content = openFile("note" + i + "Content");
                    NoteItem note = new NoteItem(title, content, "note" + i);
                    testDataset.add(note);
                }
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteItem newNote = new NoteItem("","", "note" + curNum);
                curNum++;
                SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("curNumber", curNum);
                editor.commit();
                testDataset.add(newNote);
                Intent intent = new Intent(MainActivity.this, writeNote.class);
                intent.putExtra("newNote", newNote);
                startActivity(intent);

            }
        });

        //recyclerView = (RecyclerView) findViewById(R.id.noteList);
        recyclerView = (RecyclerView) findViewById(R.id.noteList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NoteAdapter(testDataset, MainActivity.this);
        recyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_
        settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        testDataset.clear();
        mAdapter = new NoteAdapter(testDataset, MainActivity.this);
        recyclerView.setAdapter(mAdapter);
        File fileDirec = getFilesDir();
        File[] files = fileDirec.listFiles();
        int size = files.length;
        String fileName;
        String title;
        String content;
        for (int i = 1; i < curNum; i++) {
            File file = getBaseContext().getFileStreamPath("note" + i + "Title");
            File file2 = getBaseContext().getFileStreamPath("note" + i + "Content");
            if (file.exists()) {
                if (file.length() == 0 && file2.length() == 0) {
                    file.delete();
                    file2.delete();
                } else {
                    fileName = "note" + i + "Title";
                    title = openFile("note" + i + "Title");
                    content = openFile("note" + i + "Content");
                    NoteItem note = new NoteItem(title, content, "note" + i);
                    testDataset.add(note);
                }
            }
        }
    }

    public boolean FileExists(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    public String openFile(String fname) {
        String output = "";

        try {
            InputStream inputStream = openFileInput(fname);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuilder buf = new StringBuilder();
            if ((str = bufferedReader.readLine()) != null) {
                buf.append(str);
            }
            while ((str = bufferedReader.readLine()) != null) {
                buf.append("\n" + str);
            } inputStream .close();

            output = buf.toString();

        } catch (Throwable t) {

        }

        return output;
    }
}

