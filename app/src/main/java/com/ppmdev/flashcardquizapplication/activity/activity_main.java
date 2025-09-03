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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ppmdev.flashcardquizapplication.R;
import com.ppmdev.flashcardquizapplication.adapter.QuestionsAdapter;
import com.ppmdev.flashcardquizapplication.model.Question;

import java.util.ArrayList;
import java.util.List;

public class activity_main extends AppCompatActivity {
    private Button btnAdd;
    private Button btnStartQuiz;
    private RecyclerView recyclerView;
    private QuestionsAdapter questionsAdapter;
    private List<Question> questionList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAddqn);
        btnStartQuiz = findViewById(R.id.btnStartQuiz);
        recyclerView = findViewById(R.id.rvQuestions);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionList = new ArrayList<>();
        questionsAdapter = new QuestionsAdapter(questionList);
        recyclerView.setAdapter(questionsAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                questionList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                }
                questionsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(activity_main.this, activity_add_questions.class);
                startActivity(start);
            }
        });

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(activity_main.this, QuizActivity.class);
                startActivity(start);
            }
        });
    }
}
