package ru.samarin.chess;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class MoveHistoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_history);


        Intent intent = getIntent();
        ArrayList<String> moveHistory = intent.getStringArrayListExtra("moveHistoryArrayList");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);

        for(int i = 0;i<moveHistory.size()/2;i++) {
            String s = String.valueOf(i+1) + ". " + moveHistory.get(i*2) + " " + moveHistory.get(i*2+1);
            adapter.add(s);
        }
        if(moveHistory.size()%2 != 0) {
            int i = moveHistory.size()/2 + 1;
            String s = String.valueOf(i) + ". " + moveHistory.get(moveHistory.size()-1);
            adapter.add(s);
        }

        ListView lv = (ListView) findViewById(R.id.listView_moveHistory);
        lv.setAdapter(adapter);
    }
}
