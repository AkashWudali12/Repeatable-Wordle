package com.example.wordle_with_cheats;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wordle_with_cheats.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> allWords;
    EditText e11;
    EditText e12;
    EditText e13;
    EditText e14;
    EditText e15;
    EditText e21;
    EditText e22;
    EditText e23;
    EditText e24;
    EditText e25;
    EditText e31;
    EditText e32;
    EditText e33;
    EditText e34;
    EditText e35;
    EditText e41;
    EditText e42;
    EditText e43;
    EditText e44;
    EditText e45;
    EditText e51;
    EditText e52;
    EditText e53;
    EditText e54;
    EditText e55;
    EditText e61;
    EditText e62;
    EditText e63;
    EditText e64;
    EditText e65;

    EditText[] Row1;
    EditText[] Row2;
    EditText[] Row3;
    EditText[] Row4;
    EditText[] Row5;
    EditText[] Row6;

    int round;
    boolean gameOver;

    EditText[][] Grid;

    String word_to_guess;

    int wrong;
    int right;
    int half_right;
    int white;

    int score;
    int high_score;

    TextView game_stauts;
    TextView score_text;

    TextView high_score_text;

    SharedPreferences sharedPref;






    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = 0;
        score_text = findViewById(R.id.score_textView);
        high_score_text = findViewById(R.id.high_score_textView);

        sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        if(!sharedPref.contains("high_score")) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("high_score", 0);
            editor.commit();
        }
        else {
            high_score_text.setText("high score: " + sharedPref.getInt("high_score", 0));
        }

        e11 = findViewById(R.id.edit_11);
        e12 = findViewById(R.id.edit_12);
        e13 = findViewById(R.id.edit_13);
        e14 = findViewById(R.id.edit_14);
        e15 = findViewById(R.id.edit_15);
        e21 = findViewById(R.id.edit_21);
        e22 = findViewById(R.id.edit_22);
        e23 = findViewById(R.id.edit_23);
        e24 = findViewById(R.id.edit_24);
        e25 = findViewById(R.id.edit_25);
        e31 = findViewById(R.id.edit_31);
        e32 = findViewById(R.id.edit_32);
        e33 = findViewById(R.id.edit_33);
        e34 = findViewById(R.id.edit_34);
        e35 = findViewById(R.id.edit_35);
        e41 = findViewById(R.id.edit_41);
        e42 = findViewById(R.id.edit_42);
        e43 = findViewById(R.id.edit_43);
        e44 = findViewById(R.id.edit_44);
        e45 = findViewById(R.id.edit_45);
        e51 = findViewById(R.id.edit_51);
        e52 = findViewById(R.id.edit_52);
        e53 = findViewById(R.id.edit_53);
        e54 = findViewById(R.id.edit_54);
        e55 = findViewById(R.id.edit_55);
        e61 = findViewById(R.id.edit_61);
        e62 = findViewById(R.id.edit_62);
        e63 = findViewById(R.id.edit_63);
        e64 = findViewById(R.id.edit_64);
        e65 = findViewById(R.id.edit_65);

        game_stauts = findViewById(R.id.game_over);

        Row1 = new EditText[]{e11, e12, e13, e14, e15};
        Row2 = new EditText[]{e21, e22, e23, e24, e25};
        Row3 = new EditText[]{e31, e32, e33, e34, e35};
        Row4 = new EditText[]{e41, e42, e43, e44, e45};
        Row5 = new EditText[]{e51, e52, e53, e54, e55};
        Row6 = new EditText[]{e61, e62, e63, e64, e65};

        Grid = new EditText[][]{Row1, Row2, Row3, Row4, Row5, Row6};

        round = 0;

        gameOver = false;

        for (EditText etxt : Row1) {
            etxt.setFocusable(false);
        }
        for (EditText etxt : Row2) {
            etxt.setFocusable(false);
        }
        for (EditText etxt : Row3) {
            etxt.setFocusable(false);
        }
        for (EditText etxt : Row4) {
            etxt.setFocusable(false);
        }
        for (EditText etxt : Row5) {
            etxt.setFocusable(false);
        }
        for (EditText etxt : Row6) {
            etxt.setFocusable(false);
        }


        getSupportActionBar().hide();
        allWords = new ArrayList<String>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
              new InputStreamReader(getAssets().open("all_possible_words.txt"))
            );
            String mLine;
            while((mLine=reader.readLine()) != null) {
                allWords.add(mLine.toString());
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        wrong = Color.rgb(225,0,0);
        right = Color.rgb(0, 204, 0);
        half_right = Color.rgb(255, 255, 0);
        white = Color.rgb(255, 255, 255);



    }

    public int changeBackground(String word, EditText[] offRow) {
        int count = 1;
        for(int i = 0; i < word.length(); i++) {
            char guess_letter = word.charAt(i);
            char actual_letter = word_to_guess.charAt(i);
            EditText curr = offRow[i];
            if(guess_letter == actual_letter) {
                count++;
                curr.setBackgroundColor(right);
            }
            else if(word_to_guess.contains(""+guess_letter)) {
                curr.setBackgroundColor(half_right);
            }
            else {
                curr.setBackgroundColor(wrong);
            }
        }
        return count;
    }

    public void checkGuess(View view) {
        if(round == -1) {
            game_stauts.setText("Game Is Over: Click Start New Game");
        }
        else if (round < 5) {
            String word;
            word = "";
            EditText[] offRow = Grid[round];
            EditText[] onRow = Grid[round + 1];
            for (EditText etxt : offRow) {
                word += etxt.getText().toString();
            }
            if(!allWords.contains(word)) {
                game_stauts.setText("Word is not is word list");
            }
            else{
                for (EditText etxt : offRow) {
                    etxt.setFocusable(false);
                }
                for (EditText etxt : onRow) {
                    etxt.setFocusableInTouchMode(true);
                }
                int count = changeBackground(word.toLowerCase(), offRow);
                if(count == 6) {
                    game_stauts.setText("You Guessed The World Correctly: Start A New Game");
                    for(EditText[] row : Grid) {
                        for(EditText box : row) {
                            box.setFocusable(false);
                            box.setBackgroundColor(right);
                        }
                    }
                    score = score + 1;
                    score_text.setText("streak: " + ""+score);
                    int curr_best = sharedPref.getInt("high_score", 0);
                    if(score > curr_best) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("high_score", score);
                        editor.commit();
                    }
                    high_score_text.setText("high score: " + sharedPref.getInt("high_score", 0));
                    round = -1;
                }
                else {
                    round = round + 1;
                }
            }
        }
        else if (round == 5) {
            String word;
            word = "";
            EditText[] offRow = Grid[round];
            for (EditText etxt : offRow) {
                word += etxt.getText().toString();
            }
            if(!allWords.contains(word)) {
            }
            else{
                for (EditText etxt : offRow) {
                    etxt.setFocusable(false);
                }
                int count = changeBackground(word.toLowerCase(), offRow);
                if(count == 6) {
                    game_stauts.setText("You Guessed The World Correctly: Start A New Game");
                    for(EditText[] row : Grid) {
                        for(EditText box : row) {
                            box.setFocusable(false);
                            box.setBackgroundColor(right);
                        }
                    }
                    score = score + 1;
                    score_text.setText("streak: " + ""+score);
                    int curr_best = sharedPref.getInt("high_score", 0);
                    if(score > curr_best) {
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt("high_score", score);
                        editor.commit();
                    }
                    high_score_text.setText("high score: " + sharedPref.getInt("high_score", 0));
                    round = -1;
                }
                else {
                    score = 0;
                    score_text.setText("streak: " + ""+score);
                    game_stauts.setText("You have no more tries left: click start new game");
                    round = -1;
                }
            }
        }
        else {
            score = 0;
            score_text.setText("streak: " + ""+score);
            game_stauts.setText("You have no more tries left: click start new game");
            round = -1;
        }
    }

    public void startGame(View view) {
        int randomIndex = (int) Math.floor(Math.random() * allWords.size());
        word_to_guess = allWords.get(randomIndex);

        round = 0;

        game_stauts.setText("Click Start: Guess a five letter word");


        System.out.println(word_to_guess);

        for (EditText etxt : Row1) {
            etxt.setFocusableInTouchMode(true);
        }

        for(EditText[] row : Grid) {
            for(EditText box : row) {
                box.setBackgroundColor(white);
                box.setText("");
            }
        }
    }
}