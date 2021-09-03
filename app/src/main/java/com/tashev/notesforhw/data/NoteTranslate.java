package com.tashev.notesforhw.data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class NoteTranslate {

    public static class Fields {
        public final static String TITLE = "title";
        public final static String TEXT = "text";
        public final static String DATE = "date";
        public final static String IMPORTANT = "important";
    }

    public static Note documentToNote(String id, Map<String, Object> doc){
        Note answer = new Note(
                (String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.DATE),
                (String) doc.get(Fields.TEXT),
                (boolean) doc.get(Fields.IMPORTANT));
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> noteToDocument(Note note){
        Map<String, Object> answer = new HashMap<>();

        answer.put(Fields.TITLE, note.getTitle());
        answer.put(Fields.DATE, note.getDate());
        answer.put(Fields.TEXT, note.getText());
        answer.put(Fields.IMPORTANT, note.isImportant());

        return answer;
    }

}
