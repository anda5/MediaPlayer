package models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

import data.Note;

/**
 * Created by anda on 1/6/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{

    private final String DATABASE_NAME = "NoteDB";
    private final String TABLE_NAME = "note";
    private final String TITLE_NAME = "nTitle";
    private final String CONTENT_NAME = "nContent";
    private final String DATE_NAME = "nDate";
    private final String KEY_ID = "nKey";

    private final ArrayList<Note> notes= new ArrayList<Note>();

    public DatabaseHandler(Context context) {

        super(context,"NoteDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_NOTES_TABLE = "CREATE TABLE "+ TABLE_NAME+
                                    "("+ KEY_ID + " INTEGER PRIMARY KEY, "+
                                         TITLE_NAME + " TEXT, "+
                                         CONTENT_NAME+ " TEXT, "+
                                         DATE_NAME+    " LONG);";
        db.execSQL(CREATE_NOTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
         onCreate(db);
    }

    public void insert(Note note){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE_NAME, note.get_title());
        contentValues.put(CONTENT_NAME, note.get_content());
        contentValues.put(DATE_NAME, note.get_date());

        db.insert(DATABASE_NAME,null,contentValues);
        db.close();
    }

    public ArrayList<Note> getNotes(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ DATABASE_NAME;
        Cursor cursor = db.query(TABLE_NAME,new String[]{KEY_ID,TITLE_NAME,CONTENT_NAME,DATE_NAME},null,null,null,null,DATE_NAME+" DESC");
        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.set_title(cursor.getString(cursor.getColumnIndex(TITLE_NAME)));
                note.set_content(cursor.getString(cursor.getColumnIndex(CONTENT_NAME)));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String date = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(DATE_NAME))).getTime());
                note.set_date(date);
                notes.add(note);
            }while (cursor.moveToNext());
        }
        return notes;
    }
}
