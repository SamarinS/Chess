package ru.samarin.chess;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SaveGameActivity extends Activity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_game);

        editText = (EditText) findViewById(R.id.editText_game_name);

        Button button_save = (Button) findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameName = editText.getText().toString();
                if(gameName.isEmpty()) {
                    //...
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("gameName", gameName);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }



}
