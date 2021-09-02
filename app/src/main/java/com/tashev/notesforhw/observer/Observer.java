package com.tashev.notesforhw.observer;

import com.tashev.notesforhw.data.Note;

public interface Observer {
    void updateState(Note note);
}
