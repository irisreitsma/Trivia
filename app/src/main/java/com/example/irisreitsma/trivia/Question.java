package com.example.irisreitsma.trivia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Iris Reitsma on 19-5-2018.
 */

public class Question implements Serializable {

    private String question;
    private ArrayList<String> answers;
    private String correctAnswer;
    private String type;

    // getters and setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
