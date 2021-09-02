package com.tashev.notesforhw;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tashev.notesforhw.data.Note;
import com.tashev.notesforhw.data.NoteSource;

public class ListNotesAdapter extends RecyclerView.Adapter<ListNotesAdapter.ViewHolder> {

    private final static String TAG = "ListNotesAdapter";
    private NoteSource noteSource;
    private OnItemClickListener itemClickListener;

    private Fragment fragment;
    private int menuContextClickPosition;

    public ListNotesAdapter(NoteSource dataSource, Fragment fragment) {
        this.noteSource = dataSource;
        this.fragment = fragment;
    }

    public int getMenuContextClickPosition() {
        return menuContextClickPosition;
    }

    @Override
    public ListNotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListNotesAdapter.ViewHolder viewHolder, int position) {
        viewHolder.setData(noteSource.getNote(position));
    }

    @Override
    public int getItemCount() {
        return noteSource.size();
    }

    public void setData(NoteSource noteSource) {
        this.noteSource = noteSource;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView title;
        private AppCompatTextView date;
        private AppCompatTextView text;
        private ToggleButton important;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_card);
            date = itemView.findViewById(R.id.date_card);
            text = itemView.findViewById(R.id.text_card);
            important = itemView.findViewById(R.id.toggle_important);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuContextClickPosition = getAdapterPosition();
                    itemView.showContextMenu();
                    return true;
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuContextClickPosition = getAdapterPosition();
                    itemView.showContextMenu(); // TODO почему эта шляпа не работает?
                    return true;
                }
            });
        }

        public void setData(Note note) {
            title.setText(note.getTitle());
            date.setText(note.getDate());
            text.setText(note.getText());
            important.setChecked(note.isImportant());
        }
    }
}
