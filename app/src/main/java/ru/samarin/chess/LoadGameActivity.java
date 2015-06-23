package ru.samarin.chess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class LoadGameActivity extends Activity {

    final String TAG = "LOAD_GAME_ACTIVITY";
    ArrayAdapter<String> adapter;
    ArrayList<Integer> adapterIDs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);


        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent();
               intent.putExtra("id", adapterIDs.get(position));
               setResult(RESULT_OK, intent);
               finish();
           }
        });

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        ArrayList<String> names = getNames();
        for(String s:names) {
            adapter.add(s);
        }
        lv.setAdapter(adapter);
    }

    private ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        adapterIDs = new ArrayList<>();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("saved_games", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex("name");
            int idColumnIndex = cursor.getColumnIndex("id");

            do {
                names.add(cursor.getString(nameColumnIndex));
                adapterIDs.add(cursor.getInt(idColumnIndex));
            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();

        return names;
    }

    class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "ChessDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
