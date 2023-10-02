package edu.schoolapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.model.TodoModel

class TodoFragment : AppCompatActivity() {
    private var adapter: TodoAdapter? = null
    private var todoList: MutableList<TodoModel>? = null
    private lateinit var db: FirebaseFirestore
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_todo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db = FirebaseFirestore.getInstance()
        val todoListView = findViewById<ListView>(R.id.todo_list_view)
        adapter = TodoAdapter(todoList)
        todoListView.adapter = adapter

//        getTodoList();
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selectedItemId = item.itemId
        if (selectedItemId == android.R.id.home) {
            finish()
        } else if (selectedItemId == R.id.menu_add) {
            startActivity(Intent(this, ActivityAddTodo::class.java))
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getTodoList()
    }

    private fun getTodoList() {
        SchoolApp.token?.let {
            db.collection("users").document(it).collection("primaryMenus")
                .document("todo").collection("todolist").get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    todoList = mutableListOf()
                    if (!queryDocumentSnapshots.isEmpty) {
                        for (snapshots in queryDocumentSnapshots) {
                            val data = snapshots.toObject(TodoModel::class.java)
                            todoList?.add(data)
                        }
                        adapter!!.updateData(todoList)
                    }
                }
        }
    }
}