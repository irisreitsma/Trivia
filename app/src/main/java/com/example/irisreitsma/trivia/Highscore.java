package com.example.irisreitsma.trivia;

import android.support.annotation.NonNull;

/**
 * Created by Iris Reitsma on 19-5-2018.
 */

public class Highscore implements Comparable {

    private String name;
    private int score;

    // getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    @Override
    // compare scores
    public int compareTo(@NonNull Object o) {
        int compareScore = ((Highscore)o).getScore();

        if(compareScore > score) {
            return 1;
        }
        else if(compareScore < score) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
