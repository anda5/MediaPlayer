package com.anda.notes;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.Note;
import models.DatabaseHandler;


public class DisplayNotes extends ActionBarActivity {

    private DatabaseHandler databaseHandler;
    private ArrayList<Note> notes = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);

        listView = (ListView)findViewById(R.id.listNotes);

        refreshData();
    }

    public void refreshData(){
        notes.clear();

        databaseHandler = new DatabaseHandler(getApplicationContext());
        ArrayList<Note> notes1 = databaseHandler.getNotes();
        for(Note note : notes1){
            Note note1 = new Note();
            note1.set_title(note.get_title());
            note1.set_content(note.get_content());
            note1.set_date(note.get_date());
            notes.add(note1);
        }
        databaseHandler.close();

        noteAdapter = new NoteAdapter(DisplayNotes.this,R.layout.note_row,notes);
        listView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();
    }
      public class NoteAdapter extends ArrayAdapter<Note>{
      Activity activity;
      int layoutResource;

          @Override
          public int getCount() {
              return mData.size();
          }

          @Override
          public Note getItem(int position) {
              return mData.get(position);
          }

          @Override
          public int getPosition(Note item) {
              return super.getPosition(item);
          }

          @Override
          public long getItemId(int position) {
              return super.getItemId(position);
          }

          @Override
          public View getView(int position, View convertView, ViewGroup parent) {

              View row = convertView;
              ViewHolder holder = null;
              if(row==null||(row.getTag())==null){
                  LayoutInflater layoutInflater = LayoutInflater.from(activity);
                  row = layoutInflater.inflate(layoutResource,null);
                  holder = new ViewHolder();

                  holder.mTitle = (TextView)row.findViewById(R.id.name);
                  holder.mDate = (TextView)row.findViewById(R.id.theDate);

                  row.setTag(holder);
              }else {
                  holder = (ViewHolder)row.getTag();
              }

              holder.note=getItem(position);
              holder.mTitle.setText(holder.note.get_title());
              holder.mDate.setText(holder.note.get_date());
              return  row;

          }
          class ViewHolder{
              Note note;
              TextView mTitle;
              TextView mId;
              TextView mContent;
              TextView mDate;
          }

          Note note;
      ArrayList<Note> mData = new ArrayList<>();

          public NoteAdapter(Activity ct, int resource, ArrayList<Note> notes) {
              super(ct, resource, notes);
              activity = ct;
              layoutResource = resource;
              mData = notes;
              notifyDataSetChanged();
          }
      }


}
