package com.tashev.notesforhw.data;

import android.content.res.Resources;

import com.tashev.notesforhw.R;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {

    private final Resources resources;
    private List<Note> noteSource;

    public NoteSourceImpl(Resources resources) {
        noteSource = new ArrayList<>();
        this.resources = resources;
    }

    public NoteSourceImpl init() {

        String[] titles = resources.getStringArray(R.array.titlesForNotes);
        String[] dates = resources.getStringArray(R.array.datesForNotes);
        String[] texts = resources.getStringArray(R.array.textForNotes);

        for (int i = 0; i < titles.length; i++) {
            noteSource.add(new Note(titles[i], dates[i], texts[i], false));
        }

        return this;
    }

    @Override
    public int size() {
        return noteSource.size();
    }

    @Override
    public Note getNote(int position) {
        return noteSource.get(position);
    }

    @Override
    public void deleteNote(int position) {
        noteSource.remove(position);
    }

    @Override
    public void updateNote(int position, Note newNote) {
        noteSource.set(position, newNote);
    }

    @Override
    public void addNote(Note newNote) {
        noteSource.add(newNote);
    }

    @Override
    public void clearNotes() {
        noteSource.clear();
    }

    public List<Note> getNoteSource() {
        return noteSource;
    }

    public void setNoteSource(List<Note> noteSource) {
        this.noteSource = noteSource;
    }
}
