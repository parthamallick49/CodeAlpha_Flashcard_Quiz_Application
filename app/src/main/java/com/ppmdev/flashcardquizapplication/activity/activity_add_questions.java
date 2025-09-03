package com.ppmdev.flashcardquizapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ppmdev.flashcardquizapplication.R;
import com.ppmdev.flashcardquizapplication.database.FirebaseDatabaseHelper;
import com.ppmdev.flashcardquizapplication.model.Question;

public class activity_add_questions extends AppCompatActivity {

    private EditText etQuestion, etCorrectAnswer, etWrongAnswer1, etWrongAnswer2, etWrongAnswer3;
    private Button btnAddQn;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_questions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etQuestion = findViewById(R.id.etUserInputQn);
        etCorrectAnswer = findViewById(R.id.etUserCorrectAns);
        etWrongAnswer1 = findViewById(R.id.etUserWrongAns1);
        etWrongAnswer2 = findViewById(R.id.etUserWrongAns2);
        etWrongAnswer3 = findViewById(R.id.etUserWrongAns3);
        btnAddQn = findViewById(R.id.btnAddQnFromQnPage);

        dbHelper = new FirebaseDatabaseHelper();

        btnAddQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addQuestion();
            }
        });
    }

    private void addQuestion() {
        String questionText = etQuestion.getText().toString().trim();
        String correctAnswer = etCorrectAnswer.getText().toString().trim();
        String wrongAnswer1 = etWrongAnswer1.getText().toString().trim();
        String wrongAnswer2 = etWrongAnswer2.getText().toString().trim();
        String wrongAnswer3 = etWrongAnswer3.getText().toString().trim();

        if (questionText.isEmpty() || correctAnswer.isEmpty() || wrongAnswer1.isEmpty() || wrongAnswer2.isEmpty() || wrongAnswer3.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Question question = new Question(questionText, correctAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);

        dbHelper.addQuestion(question).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(activity_add_questions.this, "Question added successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity_add_questions.this, activity_main.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(activity_add_questions.this, "Failed to add question", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
