package com.tashev.notesforhw;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private int id = 0;

    private String title;

    private String description;

    private String date;

    public Note(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
        id++;
    }

    public Note() {
    }

    //region Parcelization
    protected Note(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
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

    //endregionn


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
