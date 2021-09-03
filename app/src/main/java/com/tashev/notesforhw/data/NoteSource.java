package com.tashev.notesforhw.data;

public interface NoteSource {

    NoteSource init(NoteSourceResponse noteSourceResponse);

    int size();
    Note getNote(int position);

    void deleteNote(int position);
    void updateNote(int position, Note newNote);
    void addNote(Note newNote);
    void clearNotes();

}
