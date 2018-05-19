package com.example.irisreitsma.trivia;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TriviaHelper.Callback {

    private Question question = new Question();
    private int[] buttons = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4};
    private int score;
    private int questions;
    private int MAX_QUESTIONS = 5;
    private boolean answered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // if this is first question
        if (savedInstanceState == null) {
            TriviaHelper request = new TriviaHelper(this);
            request.getNextQuestion(this);
            score = 0;
        }
        // if not first question
        else {
            // skip question if answered already
            answered = savedInstanceState.getBoolean("answered");
            if (answered) {
                nextQuestion();
            }

            // retrieve and show question
            question = (Question) savedInstanceState.getSerializable("question");
            score = savedInstanceState.getInt("score");
            questions = savedInstanceState.getInt("questions");
            viewQuestion();
        }
    }

    @Override
    // when got question
    public void gotQuestion(Question question) {
        this.question = question;
        answered = false;

        // make buttons visible
        booleanButtons(View.VISIBLE);
        viewQuestion();
    }

    @Override
    // make toast when error
    public void gotError(String message) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast.makeText(context, message, duration).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save question
        outState.putSerializable("question", question);
        outState.putLong("score", score);
        outState.putInt("questions", questions);
        outState.putBoolean("answered", answered);
        }

    // method for going to next question or input score screen
    private void nextQuestion() {
        // go to next question
        if(questions < MAX_QUESTIONS) {
            new TriviaHelper(this).getNextQuestion(this);
        }
        // go to input score screen
        else {
            Intent intent = new Intent(MainActivity.this, InputActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }
    }

    // method for when answer button is clicked
    public void buttonClicked(View v) {
        if(!(answered)) {

            answered = true;
            questions += 1;
            Button button = (Button) v;

            // check if answer was correct
            if (questions < MAX_QUESTIONS && button.getText().equals(question.getCorrectAnswer())) {
                score += 1;
            }

            nextQuestion();
        }
    }

    // method for showing question and answers
    private void viewQuestion() {

        // display question
        TextView textView = findViewById(R.id.question);
        textView.setText(question.getQuestion());

        // set boolean answers
        if(question.getType().equals("boolean")) {
            // make last two buttons invisible
            booleanButtons(View.GONE);
            Button button = findViewById(buttons[0]);
            button.setText("True");
            button = findViewById(buttons[1]);
            button.setText("False");
        }
        // set multiple choice answers
        else {
            ArrayList<String> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                Button button = findViewById(buttons[i]);
                button.setText(answers.get(i));
            }
        }
    }

    // change visibility of buttons
    private void booleanButtons(int state) {
        Button button = findViewById(buttons[2]);
        button.setVisibility(state);
        button = findViewById(buttons[3]);
        button.setVisibility(state);
    }
}
