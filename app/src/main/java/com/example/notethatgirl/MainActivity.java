package com.example.notethatgirl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<Integer> itemId = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    SQLiteDatabase itemDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        itemDB = this.openOrCreateDatabase("Item", Context.MODE_PRIVATE, null);

        itemDB.execSQL("CREATE TABLE IF NOT EXISTS items(id INTEGER PRIMARY KEY, name VARCHAR, start DATE, break DATE, phone VARCHAR, reason VARCHAR)");

        final ListView itemList = (ListView) findViewById(R.id.itemList);

        updateList();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemName);

        itemList.setAdapter(arrayAdapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("Item Id", Integer.toString(itemId.get(position)));

                Intent i = new Intent(getApplicationContext(), ShowGirl.class);

                i.putExtra("itemId", itemId.get(position));

                startActivity(i);

            }
        });

        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete a Girl")
                        .setMessage("Are you actually going to delete her?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                    itemDB.execSQL("DELETE FROM items WHERE id = " + itemId.get(position));

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });


    }

    public void updateList() {

        try {

            Cursor c = itemDB.rawQuery("SELECT * FROM items ORDER BY id DESC", null);

            int idIndex = c.getColumnIndex("id");
            int nameIndex = c.getColumnIndex("name");

            c.moveToFirst();

            itemName.clear();
            itemId.clear();

            while (c != null) {
                itemName.add(c.getString(nameIndex));
                itemId.add(c.getInt(idIndex));
                c.moveToNext();
            }

            arrayAdapter.notifyDataSetChanged();

            c.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add) {

            Intent i = new Intent(getApplicationContext(), AddANewGirl.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
