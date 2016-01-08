package com.anda.notes;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.Note;
import models.DatabaseHandler;


public class DisplayNotes extends Activity {

    private DatabaseHandler dba;
    private ArrayList<Note> dbWishes = new ArrayList<>();
    private WishAdapter wishAdapter;
    private ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);

        listView = (ListView) findViewById(R.id.listNotes);

        refreshData();



    }

    private void refreshData() {
        dbWishes.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<Note> wishesFromDB = dba.getNotes();

        for (int i = 0; i < wishesFromDB.size(); i++){

            String title = wishesFromDB.get(i).get_title();
            String dateText = wishesFromDB.get(i).get_date();
            String content = wishesFromDB.get(i).get_content();
            int mid = wishesFromDB.get(i).get_id();


            //Log.v("IDs: " , String.valueOf(mid));

            Note myWish = new Note();
            myWish.set_title(title);
            myWish.set_content(content);
            myWish.set_date(dateText);
            myWish.set_id(mid);
            dbWishes.add(myWish);


        }
        dba.close();

        //setup adapter
        wishAdapter = new WishAdapter(DisplayNotes.this,R.layout.note_row, dbWishes);
        listView.setAdapter(wishAdapter);
        wishAdapter.notifyDataSetChanged();


    }

    public class WishAdapter extends ArrayAdapter<Note>{
        Activity activity;
        int layoutResource;
        Note wish;
        ArrayList<Note> mData = new ArrayList<>();

        public WishAdapter(Activity act, int resource, ArrayList<Note> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;
            notifyDataSetChanged();


        }

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

            if ( row == null || (row.getTag()) == null){

                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.name);
                holder.mDate = (TextView) row.findViewById(R.id.date);



                row.setTag(holder);

            }else {

                holder = (ViewHolder) row.getTag();
            }

            holder.note = getItem(position);

            holder.mTitle.setText(holder.note.get_title());
            holder.mDate.setText(holder.note.get_date());



            final ViewHolder finalHolder = holder;
            holder.mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = finalHolder.note.get_content().toString();
                    String dateText = finalHolder.note.get_date().toString();
                    String title = finalHolder.note.get_title().toString();

                    int mid = finalHolder.note.get_id();

                    Log.v("MyId: " , String.valueOf(mid));


//




                    Intent i = new Intent(DisplayNotes.this, DetalisActivity.class);
                    i.putExtra("content", text);
                    i.putExtra("date", dateText);
                    i.putExtra("title", title);
                    i.putExtra("id", mid);
                    startActivity(i);
                }
            });
            return row;

        }





        class ViewHolder{
            TextView mTitle;
            int mId;
            TextView mContent;
            TextView mDate;
            public Note note;
        }

    }

}



