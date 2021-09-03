package com.tashev.notesforhw.data;


import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String id;
    private String title;
    private String text;
    private String date;
    private boolean important;

    public Note(String title, String date, String text, boolean important) {
        this.title = title;
        this.date = date;
        this.text = text;
        this.important = important;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public boolean isImportant() {
        return important;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    //region Parcelization

    protected Note(Parcel in) {
        title = in.readString();
        text = in.readString();
        date = in.readString();
        important = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(date);
        dest.writeByte((byte) (important ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    //endregion
}
