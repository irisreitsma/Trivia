package com.example.irisreitsma.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // get score
        score = getIntent().getIntExtra("score", score);

        // fill fields
        TextView textView = findViewById(R.id.result);
        textView.setText(Integer.toString(score));
    }

    public void submitClicked(View v) {

        // get name
        EditText editText = findViewById(R.id.name);

        // create highscore
        Highscore highscore = new Highscore();
        highscore.setName(editText.getText().toString());
        highscore.setScore(score);

        // submit highscore
        HighscoresHelper helper = new HighscoresHelper(this);
        helper.postNewHighscore(highscore);

        // launch highscore activity
        Intent intent = new Intent(InputActivity.this, HighscoreActivity.class);
        startActivity(intent);
    }
}
