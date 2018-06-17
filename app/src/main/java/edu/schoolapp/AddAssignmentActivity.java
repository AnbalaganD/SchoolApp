package edu.schoolapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAssignmentActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText unitEditText;
    private EditText markEditText;
    private EditText markObtainedEditText;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();

        titleEditText = findViewById(R.id.assignment_title_edit_text);
        descriptionEditText = findViewById(R.id.assignment_description_edit_text);
        unitEditText = findViewById(R.id.assignment_unit_edit_text);
        markEditText = findViewById(R.id.mark_edit_text);
        markObtainedEditText = findViewById(R.id.mark_obtained_edit_text);

        findViewById(R.id.add_assignment_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAssignmentItem();
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

    private void addAssignmentItem() {
        if (ValidateData()) {
            CollectionReference assignment = db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").document("assignment").collection("assignmentList");
            Map<String, Object> assignmentModel = new HashMap<>();
            assignmentModel.put("title", titleEditText.getText().toString());
            assignmentModel.put("description", descriptionEditText.getText().toString());
            assignmentModel.put("unit", unitEditText.getText().toString());
            assignmentModel.put("mark", Integer.parseInt(markEditText.getText().toString()));
            assignmentModel.put("markObtained", Integer.parseInt(markObtainedEditText.getText().toString()));
            assignment.add(assignmentModel);
            Toast.makeText(this, "Add assignment successfully...", Toast.LENGTH_SHORT).show();
            clearData();
        }
    }

    private void clearData() {
        titleEditText.setText(null);
        descriptionEditText.setText(null);
        unitEditText.setText(null);
        markEditText.setText(null);
        markObtainedEditText.setText(null);
    }

    private boolean ValidateData() {
        boolean isValid = true;
        if (titleEditText.getText() == null || titleEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (unitEditText.getText() == null || unitEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Unit", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (descriptionEditText.getText() == null || descriptionEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (markEditText.getText() == null || markEditText.getText().toString().trim().isEmpty() || !tryParseInt(markEditText.getText().toString().trim())) {
            Toast.makeText(this, "Enter Mark", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else if (markObtainedEditText.getText() == null || markObtainedEditText.getText().toString().trim().isEmpty() || !tryParseInt(markObtainedEditText.getText().toString().trim())) {
            Toast.makeText(this, "Enter Mark Obtained", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    private boolean tryParseInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
