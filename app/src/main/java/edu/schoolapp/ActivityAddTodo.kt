package edu.schoolapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.SchoolApp.Companion.token

class ActivityAddTodo : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db = FirebaseFirestore.getInstance()
        titleEditText = findViewById(R.id.title_edit_text)
        descriptionEditText = findViewById(R.id.description_edit_view)
        dateEditText = findViewById(R.id.date_edit_text)
        findViewById<View>(R.id.add_todo_button).setOnClickListener { addTodoItem() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addTodoItem() {
        if (validateData()) {
            val todolist = db.collection("users").document(token!!).collection("primaryMenus")
                .document("todo").collection("todolist")
            val todoModel: MutableMap<String, Any> = HashMap()
            todoModel["title"] = titleEditText.text.toString()
            todoModel["description"] = descriptionEditText.text.toString()
            todoModel["date"] = "1/5/2018"
            todolist.add(todoModel)
            Toast.makeText(this, "Add todo successfully...", Toast.LENGTH_SHORT).show()
            clearData()
        }
    }

    private fun clearData() {
        titleEditText.setText("")
        descriptionEditText.setText("")
        dateEditText.setText(getString(R.string.select_date))
    }

    private fun validateData(): Boolean {
        var isValid = true
        if (titleEditText.text == null || titleEditText.text.toString().trim { it <= ' ' }
                .isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (descriptionEditText.text == null || descriptionEditText.text.toString()
                .trim { it <= ' ' }
                .isEmpty()
        ) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }
}