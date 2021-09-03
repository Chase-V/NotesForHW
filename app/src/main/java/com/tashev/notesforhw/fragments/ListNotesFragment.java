package com.tashev.notesforhw.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tashev.notesforhw.ListNotesAdapter;
import com.tashev.notesforhw.MainActivity;
import com.tashev.notesforhw.Navigation;
import com.tashev.notesforhw.R;
import com.tashev.notesforhw.data.Note;
import com.tashev.notesforhw.data.NoteSource;
import com.tashev.notesforhw.data.NoteSourceLocalImpl;
import com.tashev.notesforhw.data.NoteSourceRemoteImpl;
import com.tashev.notesforhw.data.NoteSourceResponse;
import com.tashev.notesforhw.observer.Observer;
import com.tashev.notesforhw.observer.Publisher;

public class ListNotesFragment extends Fragment {

    public static String KEY_NOTE = "note";
    boolean isLandscape;
    private NoteSource noteSource;
    private ListNotesAdapter adapter;
    private RecyclerView recyclerView;
    private Note currentNote;
    private Publisher publisher;
    private Navigation navigation;
    private FloatingActionButton buttonAdd;

    public static ListNotesFragment newInstance() {
        return new ListNotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(KEY_NOTE);
        }

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) showNoteEditor(currentNote, false);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        navigation = null;
        super.onDetach();
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable(KEY_NOTE, currentNote);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.list_notes_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        initRecyclerView(recyclerView, noteSource);

        if (false) {
            noteSource = new NoteSourceLocalImpl(getResources()).init(new NoteSourceResponse() {
                @Override
                public void initialized(NoteSource noteSource) {

                }
            });
        } else {
            noteSource = new NoteSourceRemoteImpl().init(new NoteSourceResponse() {
                @Override
                public void initialized(NoteSource noteSource) {
                    adapter.notifyDataSetChanged();
                }
            });
        }
        initButtonAdd(view);
        adapter.setNoteSource(noteSource);
        adapter.setOnItemClickListener(new ListNotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.d("mylog", "posittion" + position);
                Log.d("mylog", "size" + noteSource.size());
                showNoteEditor(noteSource.getNote(position), true);

                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(Note note) {
                        noteSource.updateNote(position, note);
                        adapter.notifyItemChanged(position-1);
                    }
                });
            }
        });
        return view;
    }

    public void initRecyclerView(RecyclerView recyclerView, NoteSource noteSource) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListNotesAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    public void showNoteEditor(Note note, boolean useBackStack) {
        if (!isLandscape) {
            navigation.addFragment(R.id.note_container, EditNoteFragment.newInstance(note), useBackStack);
        } else {
            navigation.addFragment(R.id.edit_note_container, EditNoteFragment.newInstance(note), useBackStack);
        }
    }

    private void initButtonAdd(View view) {

        buttonAdd = view.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showNoteEditor(null, true);
                publisher.subscribe(new Observer() {

                    @Override
                    public void updateState(Note note) {
                        noteSource.addNote(note);
                        adapter.notifyItemInserted(noteSource.size() - 1);
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_notes_clear:
                noteSource.clearNotes();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getMenuContextClickPosition();
        if (item.getItemId() == R.id.context_remove) {
            noteSource.deleteNote(position);
            adapter.notifyItemRemoved(position);
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
