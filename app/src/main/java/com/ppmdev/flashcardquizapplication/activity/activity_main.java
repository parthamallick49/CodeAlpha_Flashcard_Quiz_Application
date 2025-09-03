package com.ppmdev.flashcardquizapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ppmdev.flashcardquizapplication.R;
import com.ppmdev.flashcardquizapplication.adapter.QuestionAdapter;
import com.ppmdev.flashcardquizapplication.database.FirebaseDatabaseHelper;
import com.ppmdev.flashcardquizapplication.model.Question;

import java.util.ArrayList;
import java.util.List;

public class activity_main extends AppCompatActivity {

    private RecyclerView rvQuestions;
    private Button btnAddQuestion;
    private QuestionAdapter questionAdapter;
    private List<Question> questionList;
    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvQuestions = findViewById(R.id.rvQuestions);
        btnAddQuestion = findViewById(R.id.btnAddqn);

        rvQuestions.setLayoutManager(new LinearLayoutManager(this));
        questionList = new ArrayList<>();
        questionAdapter = new QuestionAdapter(questionList);
        rvQuestions.setAdapter(questionAdapter);

        dbHelper = new FirebaseDatabaseHelper();

        loadQuestions();

        btnAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_main.this, activity_add_questions.class);
                startActivity(intent);
            }
        });
    }

    private void loadQuestions() {
        dbHelper.getQuestions().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                }
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
