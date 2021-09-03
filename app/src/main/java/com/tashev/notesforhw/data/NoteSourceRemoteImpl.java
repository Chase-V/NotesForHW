package com.tashev.notesforhw.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceRemoteImpl implements NoteSource {

    private static String CARDS_COLLECTIONS = "cards";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    private CollectionReference collectionReference = store.collection(CARDS_COLLECTIONS);

    private List<Note> cardsData = new ArrayList<Note>();

    @Override
    public NoteSource init(NoteSourceResponse noteSourceResponse) {
        collectionReference.orderBy(NoteTranslate.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            cardsData = new ArrayList<Note>();
                            for (QueryDocumentSnapshot docField : task.getResult()) {
                                Note note = NoteTranslate.documentToNote(docField.getId(), docField.getData());
                                cardsData.add(note);
                            }
                            noteSourceResponse.initialized(NoteSourceRemoteImpl.this);
                        }
                    }
                });
        return this;
    }

    @Override
    public int size() {
        return cardsData.size();
    }

    @Override
    public Note getNote(int position) {
        return cardsData.get(position);
    }

    @Override
    public void deleteNote(int position) {
        collectionReference.document(cardsData.get(position).getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });
//        cardsData.remove(position);
    }

    @Override
    public void updateNote(int position, Note newNote) {
        collectionReference.document(cardsData.get(position).getId())
                .update(NoteTranslate.noteToDocument(newNote));
    }

    @Override
    public void addNote(Note newNote) {
        collectionReference.add(NoteTranslate.noteToDocument(newNote));
    }

    @Override
    public void clearNotes() {
        for (Note note : cardsData) {
            collectionReference.document(note.getId()).delete();
        }
    }
}
