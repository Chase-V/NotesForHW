package com.tashev.notesforhw.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.tashev.notesforhw.MainActivity;
import com.tashev.notesforhw.R;
import com.tashev.notesforhw.data.Note;
import com.tashev.notesforhw.observer.Publisher;

public class EditNoteFragment extends Fragment {

    public static String ARG_NOTE = "note";
    private Note note;
    private Publisher publisher;

    private EditText editTitle;
    private EditText editDate;
    private EditText editText;
    private DatePicker datePicker;
    private ToggleButton important;
    private boolean isImportant;
    private AppCompatButton buttonSave;


    public static EditNoteFragment newInstance(Note note) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static EditNoteFragment newInstance() {
        EditNoteFragment fragment = new EditNoteFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.note = getArguments().getParcelable(ARG_NOTE);
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(ARG_NOTE, note);
//    }
//
//    @Override
//    public void onViewStateRestored(Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if (savedInstanceState != null) note = savedInstanceState.getParcelable(ARG_NOTE);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_note_fragment, container, false);
        initViews(view);

        if (note != null) {
            setNoteContent();
        }

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }

    @SuppressLint("NewApi")
    private void initViews(View view) {
        editTitle = view.findViewById(R.id.title_note);
        editDate = view.findViewById(R.id.date_note);
        editText = view.findViewById(R.id.text_note);
        datePicker = view.findViewById(R.id.date_picker_note);
        important = view.findViewById(R.id.toggle_important);
        buttonSave = view.findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = collectNoteData();

                publisher.notifyTask(note);

                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.note_container, ListNotesFragment.newInstance())
                        .commit();
            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setVisibility(View.VISIBLE);
            }
        });

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
            }
        });

        editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                datePicker.setVisibility(View.GONE);
            }
        });
    }

    private void setNoteContent() {
        editTitle.setText(this.note.getTitle());
        editDate.setText(this.note.getDate());
        editText.setText(this.note.getText());
        important.setChecked(isImportant);
    }

    private Note collectNoteData() {
        String title = this.editTitle.getText().toString();
        String date = this.editDate.getText().toString();
        String text = this.editText.getText().toString();
        isImportant = important.isChecked();

        if (note != null) {

            note.setTitle(title);
            note.setDate(date);
            note.setText(text);
            return note;
        } else{
            isImportant = false;
            return new Note(title, date, text, isImportant);
        }


    }
}
