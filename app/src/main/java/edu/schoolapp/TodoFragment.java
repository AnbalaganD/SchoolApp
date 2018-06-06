package edu.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.schoolapp.Model.PrimaryMenuDao;
import edu.schoolapp.Model.TodoModel;

public class TodoFragment extends AppCompatActivity {

    private TodoAdapter adapter;
    private List<TodoModel> todoList;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();

        ListView todoListView = findViewById(R.id.todo_list_view);
        adapter = new TodoAdapter(todoList);
        todoListView.setAdapter(adapter);

//        getTodoList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == android.R.id.home) {
            finish();
        } else if (selectedItemId == R.id.menu_add) {
            startActivity(new Intent(this, ActivityAddTodo.class));
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTodoList();
    }

    private void getTodoList() {
        db.collection("users").document(SchoolApp.getToken()).collection("primaryMenus").document("todo").collection("todolist").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                todoList = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (QueryDocumentSnapshot snapshots : queryDocumentSnapshots) {
                        TodoModel data = snapshots.toObject(TodoModel.class);
                        todoList.add(data);
                    }
                    adapter.updateData(todoList);
                }
            }
        });
    }
}
