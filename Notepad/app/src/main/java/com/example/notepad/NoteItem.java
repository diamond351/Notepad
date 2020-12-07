package com.example.notepad;

import java.io.Serializable;

public class NoteItem implements Serializable {
    String title;
    String content;
    String fileName;

    public NoteItem(String title, String content, String fileName) {
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }

    public NoteItem(String title, String content) {
        this.title = title;
        this.content = content;
        fileName = "";
    }
    public NoteItem() {
        this.title = "";
        this.content = "";
        fileName = "";
    }
}
