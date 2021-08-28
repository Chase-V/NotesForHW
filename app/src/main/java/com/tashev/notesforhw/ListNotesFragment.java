package com.tashev.notesforhw;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class ListNotesFragment extends Fragment {

    Note currentNote;
    public static String KEY_NOTE = "note";
    boolean isLandscape;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(KEY_NOTE);
        }

        if (isLandscape)
            if (currentNote != null) {
                showNote(currentNote.getId());
            } else {
                showNote(0);
            }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_notes_fragment, container, false);

        LinearLayout linearLayout = (LinearLayout) view;

        String[] titles = getResources().getStringArray(R.array.titlesForNotes);
        String[] dates = getResources().getStringArray(R.array.datesForNotes);
        String[] texts = getResources().getStringArray(R.array.textForNotes);

        for (int i = 0; i < titles.length; i++) {

            LinearLayout linearView = new LinearLayout(requireActivity());
            linearView.setOrientation(LinearLayout.VERTICAL);
            TextView titleView = new TextView(requireActivity());
            TextView dateView = new TextView(requireActivity());
            TextView textView = new TextView(requireActivity());
            int index = i;

            linearView.setPadding(16, 8, 16, 8);
            linearLayout.setBackgroundColor(Color.parseColor("#FFEFCC"));
            if (i % 2 == 0) {linearView.setBackgroundColor(Color.parseColor("#FFD9C5"));}

            Note note = new Note(titles[i], dates[i], texts[i]);

            titleView.setTextSize(18);
            dateView.setTextSize(14);
            textView.setTextSize(16);

            String title = titles[i];
            String date = dates[i];
            String text = texts[i];

            titleView.setText(note.getTitle());
            dateView.setText(note.getDate());
            textView.setText(note.getDescription());

            linearView.addView(titleView);
            linearView.addView(dateView);
            linearView.addView(textView);

            linearView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNote(index);
                }
            });

            linearLayout.addView(linearView);

        }

        AppCompatButton buttonAdd = new AppCompatButton(requireActivity());
        buttonAdd.setText("Добавить заметку");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNote(-1);
            }
        });
        linearLayout.addView(buttonAdd);

        return view;
    }

    private void showNote(int index) {
        if (index == -1) {
            currentNote = new Note();
        }else {
            currentNote = new Note(
                    getResources().getStringArray(R.array.titlesForNotes)[index]
                    , getResources().getStringArray(R.array.datesForNotes)[index]
                    , getResources().getStringArray(R.array.textForNotes)[index]);
        }
        if (isLandscape) showNotesLandscape();
        else showNotesPortrait();
    }

    private void showNotesPortrait() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.note_container, CreateNoteFragment.newInstance(currentNote))
                .addToBackStack("")
                .commit();
    }

    private void showNotesLandscape() {
        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.create_note_container, CreateNoteFragment.newInstance(currentNote))
                .commit();
    }
}
