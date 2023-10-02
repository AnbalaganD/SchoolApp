package edu.schoolapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ActivityAddTodo extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText dateEditText;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        titleEditText = findViewById(R.id.title_edit_text);
        descriptionEditText = findViewById(R.id.description_edit_view);
        dateEditText = findViewById(R.id.date_edit_text);

        findViewById(R.id.add_todo_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodoItem();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTodoItem() {
        if (ValidateData()) {
            CollectionReference todolist = db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").document("todo").collection("todolist");
            Map<String, Object> todoModel = new HashMap<>();
            todoModel.put("title", titleEditText.getText().toString());
            todoModel.put("description", descriptionEditText.getText().toString());
            todoModel.put("date", "1/5/2018");
            todolist.add(todoModel);
            Toast.makeText(this, "Add todo successfully...", Toast.LENGTH_SHORT).show();
            clearData();
        }
    }

    private void clearData() {
        titleEditText.setText("");
        descriptionEditText.setText("");
        dateEditText.setText("Select date");
    }

    private boolean ValidateData() {
        boolean isValid = true;
        if (titleEditText.getText() == null || titleEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (descriptionEditText.getText() == null || descriptionEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }
}