package edu.schoolapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.schoolapp.Model.AssignmentModel;

public class AssignmentActivity extends AppCompatActivity {

    private AssignmentAdapter assignmentAdapter;
    private List<AssignmentModel> assignmentList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_assignment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        RecyclerView assignmentRecyclerView = findViewById(R.id.assignment_recycler_view);
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        assignmentAdapter = new AssignmentAdapter(null);
        assignmentRecyclerView.setAdapter(assignmentAdapter);

        findViewById(R.id.add_assignment_fab_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssignmentActivity.this, AddAssignmentActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assignment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == android.R.id.home) {
            finish();
        } else if (selectedItemId == R.id.status_menu_item) {
            SchoolApp.data = assignmentList;
            startActivity(new Intent(this, AssignmentStatusActivity.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAssignmentList();
    }

    private void getAssignmentList() {
        db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").document("assignment").collection("assignmentList").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                assignmentList = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                        AssignmentModel data = snapshots.toObject(AssignmentModel.class);
                        assignmentList.add(data);
                    }
                    assignmentAdapter.updateData(assignmentList);
                }
            }
        });
    }
}
