package com.example.irisreitsma.trivia;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoresHelper.CallBack{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // get highscores
        HighscoresHelper helper = new HighscoresHelper(this);
        helper.getHighscores(this);
    }

    @Override
    // attach to listview
    public void gotHighscores(ArrayList<Highscore> highscores) {
        ListView listView = findViewById(R.id.highscores);
        listView.setAdapter(new HighscoresAdapter(this, R.layout.highscore, highscores));
    }

    @Override
    // make toast when error
    public void gotError(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, message, duration).show();
    }
}
