package com.tashev.notesforhw;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class CreateNoteFragment extends Fragment {

    public static String ARG_NOTE = "note";
    private Note note;

    public static CreateNoteFragment newInstance(Note note) {
        CreateNoteFragment fragment = new CreateNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.note_fragment, container, false);
        initViews(view);

        return view;
    }

    @SuppressLint("NewApi")
    private void initViews(View view) {
        EditText editTitle = view.findViewById(R.id.title_note);
        EditText editDate = view.findViewById(R.id.date_note);
        EditText editText = view.findViewById(R.id.text_note);
        DatePicker datePicker = view.findViewById(R.id.date_picker_note);
        AppCompatButton buttonBack = view.findViewById(R.id.button_back);

        if (this.note.getTitle() != null && this.note.getDate() != null && this.note.getDescription() != null){
            editTitle.setText(this.note.getTitle());
            editDate.setText(this.note.getDate());
            editText.setText(this.note.getDescription());
        }

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

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.note_container, ListNotesFragment.newInstance())
                        .commit();
            }
        });

    }

}
