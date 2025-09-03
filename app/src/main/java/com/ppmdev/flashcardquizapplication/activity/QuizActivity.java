package com.ppmdev.flashcardquizapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ppmdev.flashcardquizapplication.R;
import com.ppmdev.flashcardquizapplication.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private List<Question> questionList;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tvQuestion);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);

        questionList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");

        fetchQuestions();

        btnOption1.setOnClickListener(v -> checkAnswer(btnOption1.getText().toString()));
        btnOption2.setOnClickListener(v -> checkAnswer(btnOption2.getText().toString()));
        btnOption3.setOnClickListener(v -> checkAnswer(btnOption3.getText().toString()));
        btnOption4.setOnClickListener(v -> checkAnswer(btnOption4.getText().toString()));
    }

    private void fetchQuestions() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                }
                Collections.shuffle(questionList);
                displayQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(QuizActivity.this, "Failed to load questions.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questionList.size()) {
            Question currentQuestion = questionList.get(currentQuestionIndex);
            tvQuestion.setText(currentQuestion.getQuestion());

            List<String> options = new ArrayList<>();
            options.add(currentQuestion.getCorrectAnswer());
            options.add(currentQuestion.getWrongAnswer1());
            options.add(currentQuestion.getWrongAnswer2());
            options.add(currentQuestion.getWrongAnswer3());
            Collections.shuffle(options);

            btnOption1.setText(options.get(0));
            btnOption2.setText(options.get(1));
            btnOption3.setText(options.get(2));
            btnOption4.setText(options.get(3));
        } else {
            endQuiz();
        }
    }

    private void checkAnswer(String selectedAnswer) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            score++;
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show();
        }
        currentQuestionIndex++;
        displayQuestion();
    }

    private void endQuiz() {
        Toast.makeText(this, "Quiz ended. Your score: " + score, Toast.LENGTH_LONG).show();
        finish();
    }
}
