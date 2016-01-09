package com.anda.notes;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DetalisActivity extends ActionBarActivity {

    private TextView _titleText;
    private TextView _contentText;
    private TextView _dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalis);
        _titleText = (TextView)findViewById(R.id.title);
        _contentText = (TextView)findViewById(R.id.theNote);
        _dateText = (TextView)findViewById(R.id.theDate);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            String title = (String) bundle.get("title");
            String content = (String) bundle.get("content");
            String date = (String) bundle.get("date");
            _titleText.setText(title);
            _contentText.setText(content);
            _dateText.setText(date);
        }
    }
}
