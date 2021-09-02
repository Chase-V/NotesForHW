package com.tashev.notesforhw.observer;

import com.tashev.notesforhw.data.Note;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        this.observers = new ArrayList<Observer>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyTask(Note note) {
        for (Observer observer : observers) {
            observer.updateState(note);
            unsubscribe(observer);
        }
    }
}
