package com.example.irisreitsma.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Iris Reitsma on 19-5-2018.
 */

public class TriviaHelper implements Response.Listener<JSONObject>, Response.ErrorListener{

    // callback method
    public interface Callback {
        void gotQuestion(Question question);
        void gotError(String message);
    }

    private Callback delegate;
    private Context context;

    @Override
    // get error contents
    public void onErrorResponse(VolleyError error) {
        delegate.gotError(error.getMessage());
    }

    @Override
    // transform JSONObject
    public void onResponse(JSONObject response) {
        Question question = new Question();

        // extract results from response
        try {
            JSONArray array = response.getJSONArray("results");
            JSONObject jsonObject = array.getJSONObject(0);
            question.setQuestion(convertString(jsonObject.getString("question")));
            question.setCorrectAnswer(convertString(jsonObject.getString("correct_answer")));
            question.setType(jsonObject.getString("type"));

            // add wrong answers
            ArrayList<String> answers = new ArrayList<>();
            JSONArray wrongAnswers = jsonObject.getJSONArray("incorrect_answers");
            answers.add(convertString(jsonObject.getString("correct_answer")));
            for(int i = 0; i < wrongAnswers.length(); i++) { answers.add(convertString(wrongAnswers.getString(i))); }

            // shuffle answers
            Collections.shuffle(answers);
            question.setAnswers(answers);

        }
        catch (JSONException j) {System.out.println(j.getMessage());}

        // pass question
        delegate.gotQuestion(question);
    }

    // constuctor
    public TriviaHelper(Context context) {
        this.context = context;
    }

    // method for retrieving results from API
    public void getNextQuestion(Callback activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        delegate = activity;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://opentdb.com/api.php?amount=1&encode=urlLegacy",null, this, this);
        queue.add(jsonObjectRequest);
    }

    // make normal string from coded string
    private String convertString(String text) {
        String decodedText = "";
        try {
            decodedText = URLDecoder.decode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            delegate.gotError(e.getMessage());
        }
        return decodedText;
    }
}
