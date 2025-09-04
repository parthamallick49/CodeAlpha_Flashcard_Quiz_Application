
package com.ppmdev.flashcardquizapplication.database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ppmdev.flashcardquizapplication.model.Question;

public class FirebaseDatabaseHelper {
    private DatabaseReference databaseReference;

    public FirebaseDatabaseHelper() {
        databaseReference = FirebaseDatabase.getInstance().getReference("questions");
    }

    public Task<Void> addQuestion(Question question) {
        return databaseReference.push().setValue(question);
    }

    public Query getQuestions() {
        return databaseReference;
    }
}
