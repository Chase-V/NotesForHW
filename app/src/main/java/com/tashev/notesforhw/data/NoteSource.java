package com.tashev.notesforhw.data;

public interface NoteSource {

    int size();
    Note getNote(int position);
    NoteSource init();

    void deleteNote(int position);
    void updateNote(int position, Note newNote);
    void addNote(Note newNote);
    void clearNotes();

}
