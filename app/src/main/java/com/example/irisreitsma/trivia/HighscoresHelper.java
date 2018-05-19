package com.example.irisreitsma.trivia;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Iris Reitsma on 19-5-2018.
 */

public class HighscoresHelper {
    public interface CallBack {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotError(String message);
    }

    private Context context;
    private CallBack delegate;
    private DatabaseReference database;

    // constructor
    public HighscoresHelper(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance().getReference("highscores");
    }

    // add highscore to database
    public void postNewHighscore(Highscore highscore) {
        DatabaseReference ref = database.push();
        ref.setValue(highscore);
    }

    // get highscores
    public void getHighscores(CallBack activity) {
        delegate = activity;
        database.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Highscore> highscores = new ArrayList<>();
                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    highscores.add(child.getValue(Highscore.class));
                }
                // sort scores
                Collections.sort(highscores);
                delegate.gotHighscores(highscores);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                delegate.gotError(databaseError.getMessage());
            }
        });
    }
}
