package edu.schoolapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.SchoolApp.Companion.token

class AddAssignmentActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var unitEditText: EditText
    private lateinit var markEditText: EditText
    private lateinit var markObtainedEditText: EditText
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        db = FirebaseFirestore.getInstance()
        titleEditText = findViewById(R.id.assignment_title_edit_text)
        descriptionEditText = findViewById(R.id.assignment_description_edit_text)
        unitEditText = findViewById(R.id.assignment_unit_edit_text)
        markEditText = findViewById(R.id.mark_edit_text)
        markObtainedEditText = findViewById(R.id.mark_obtained_edit_text)
        findViewById<View>(R.id.add_assignment_button).setOnClickListener { addAssignmentItem() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addAssignmentItem() {
        if (validateData()) {
            val assignment = db.collection("users").document(token!!).collection("primaryMenus")
                .document("assignment").collection("assignmentList")
            val assignmentModel: MutableMap<String, Any> = HashMap()
            assignmentModel["title"] = titleEditText.text.toString()
            assignmentModel["description"] = descriptionEditText.text.toString()
            assignmentModel["unit"] = unitEditText.text.toString()
            assignmentModel["mark"] = markEditText.text.toString().toInt()
            assignmentModel["markObtained"] = markObtainedEditText.text.toString().toInt()
            assignment.add(assignmentModel)
            Toast.makeText(this, "Add assignment successfully...", Toast.LENGTH_SHORT).show()
            clearData()
        }
    }

    private fun clearData() {
        titleEditText.text = null
        descriptionEditText.text = null
        unitEditText.text = null
        markEditText.text = null
        markObtainedEditText.text = null
    }

    private fun validateData(): Boolean {
        var isValid = true
        if (titleEditText.text == null || titleEditText.text.toString().trim { it <= ' ' }
                .isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (unitEditText.text == null || unitEditText.text.toString().trim { it <= ' ' }
                .isEmpty()) {
            Toast.makeText(this, "Enter Unit", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (descriptionEditText.text == null || descriptionEditText.text.toString()
                .trim { it <= ' ' }
                .isEmpty()
        ) {
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (markEditText.text == null || markEditText.text.toString().trim { it <= ' ' }
                .isEmpty() || !tryParseInt(
                markEditText.text.toString().trim { it <= ' ' })
        ) {
            Toast.makeText(this, "Enter Mark", Toast.LENGTH_SHORT).show()
            isValid = false
        } else if (markObtainedEditText.text == null || markObtainedEditText.text.toString()
                .trim { it <= ' ' }
                .isEmpty() || !tryParseInt(
                markObtainedEditText.text.toString().trim { it <= ' ' })
        ) {
            Toast.makeText(this, "Enter Mark Obtained", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }

    private fun tryParseInt(s: String): Boolean {
        try {
            s.toInt()
        } catch (ex: Exception) {
            return false
        }
        return true
    }
}