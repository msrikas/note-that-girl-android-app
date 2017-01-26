package com.example.notethatgirl;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddANewGirl extends AppCompatActivity {

    SQLiteDatabase itemDB;

    public void saveItem(View view) {

        EditText name = (EditText) findViewById(R.id.name);
        EditText startDate = (EditText) findViewById(R.id.startDate);
        EditText breakDate = (EditText) findViewById(R.id.breakupDate);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText reason = (EditText) findViewById(R.id.reason);

        String query = "INSERT INTO items (name, start, break, phone, reason) VALUES (?, ?, ?, ?, ?)";

        try {

            SQLiteStatement statement = itemDB.compileStatement(query);

            statement.bindString(1, name.getText().toString());
            statement.bindString(2, startDate.getText().toString());
            statement.bindString(3, breakDate.getText().toString());
            statement.bindString(4, phone.getText().toString());
            statement.bindString(5, reason.getText().toString());

            statement.execute();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(i);

        } catch (Exception e) {

            e.getMessage().toString();

        }

//        try {
//
//            Cursor c = itemDB.rawQuery("SELECT * FROM items ORDER BY id DESC", null);
//
//            int nameIndex = c.getColumnIndex("name");
//            int startIndex = c.getColumnIndex("start");
//            int breakIndex = c.getColumnIndex("break");
//            int phoneIndex = c.getColumnIndex("phone");
//            int reasonIndex = c.getColumnIndex("reason");
//
//            c.moveToFirst();
//
//            while (c != null) {
//                Log.i("Name", c.getString(nameIndex));
//                Log.i("Start Date", c.getString(startIndex));
//                Log.i("Break Date", c.getString(breakIndex));
//                Log.i("Phone", c.getString(phoneIndex));
//                Log.i("Reason", c.getString(reasonIndex));
//                c.moveToNext();
//            }
//
//            c.close();
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_new_girl);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemDB = this.openOrCreateDatabase("Item", Context.MODE_PRIVATE, null);

    }

}
