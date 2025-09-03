package com.ppmdev.flashcardquizapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ppmdev.flashcardquizapplication.R;
import com.ppmdev.flashcardquizapplication.model.Question;

public class activity_add_questions extends AppCompatActivity {

    private EditText etQuestion, etCorrectAnswer, etWrongAnswer1, etWrongAnswer2, etWrongAnswer3;
    private Button btnAddQn;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);

        etQuestion = findViewById(R.id.etUserInputQn);
        etCorrectAnswer = findViewById(R.id.etUserCorrectAns);
        etWrongAnswer1 = findViewById(R.id.etUserWrongAns1);
        etWrongAnswer2 = findViewById(R.id.etUserWrongAns2);
        etWrongAnswer3 = findViewById(R.id.etUserWrongAns3);
        btnAddQn = findViewById(R.id.btnAddQnFromQnPage);

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");

        btnAddQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
            }
        });
    }

    private void addQuestion() {
        String question = etQuestion.getText().toString().trim();
        String correctAnswer = etCorrectAnswer.getText().toString().trim();
        String wrongAnswer1 = etWrongAnswer1.getText().toString().trim();
        String wrongAnswer2 = etWrongAnswer2.getText().toString().trim();
        String wrongAnswer3 = etWrongAnswer3.getText().toString().trim();

        if (question.isEmpty() || correctAnswer.isEmpty() || wrongAnswer1.isEmpty() || wrongAnswer2.isEmpty() || wrongAnswer3.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        Question newQuestion = new Question(question, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);

        if (id != null) {
            databaseReference.child(id).setValue(newQuestion);
            Toast.makeText(this, "Question added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add question", Toast.LENGTH_SHORT).show();
        }
    }
}
