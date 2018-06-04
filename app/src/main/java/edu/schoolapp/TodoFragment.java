package edu.schoolapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class TodoFragment extends AppCompatActivity {

    TodoAdapter adapter;
    private List<TodoModel> todoList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_todo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ListView todoListView = findViewById(R.id.todo_list_view);
        adapter = new TodoAdapter(todoList);
        todoListView.setAdapter(adapter);
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
}
