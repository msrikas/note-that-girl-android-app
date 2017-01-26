package com.example.notethatgirl;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ShowGirl extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_girl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Show Girl");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView name = (TextView) findViewById(R.id.name);
        TextView start = (TextView) findViewById(R.id.start);
        TextView breakup = (TextView) findViewById(R.id.breakup);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView reason = (TextView) findViewById(R.id.reason);

        Intent i = getIntent();

        int itemId = i.getIntExtra("itemId", -1);

        if (itemId != -1) {

            SQLiteDatabase itemDB = this.openOrCreateDatabase("Item", Context.MODE_PRIVATE, null);

            try {

                Cursor c = itemDB.rawQuery("SELECT * FROM items WHERE id = " + String.valueOf(itemId), null);

                int nameIndex = c.getColumnIndex("name");
                int startIndex = c.getColumnIndex("start");
                int breakIndex = c.getColumnIndex("break");
                int phoneIndex = c.getColumnIndex("phone");
                int reasonIndex = c.getColumnIndex("reason");

                c.moveToFirst();

                while (c != null) {
                    toolbar.setTitle(c.getString(nameIndex) + " Details");
                    name.setText(c.getString(nameIndex));
                    start.setText(c.getString(startIndex));
                    breakup.setText(c.getString(breakIndex));
                    phone.setText(c.getString(phoneIndex));
                    reason.setText(c.getString(reasonIndex));
                    c.moveToNext();
                }

                c.close();
                itemDB.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
