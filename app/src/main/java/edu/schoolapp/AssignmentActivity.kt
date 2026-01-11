package edu.schoolapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import edu.schoolapp.model.AssignmentModel

class AssignmentActivity : AppCompatActivity() {
    private lateinit var assignmentAdapter: AssignmentAdapter
    private lateinit var assignmentList: MutableList<AssignmentModel>
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_assignment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db = FirebaseFirestore.getInstance()
        val assignmentRecyclerView: RecyclerView =
            findViewById(R.id.assignment_recycler_view)
        assignmentRecyclerView.layoutManager = LinearLayoutManager(this)
        assignmentAdapter = AssignmentAdapter(null)
        assignmentRecyclerView.adapter = assignmentAdapter
        findViewById<View>(R.id.add_assignment_fab_button).setOnClickListener {
            startActivity(
                Intent(this@AssignmentActivity, AddAssignmentActivity::class.java)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_assignment, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selectedItemId = item.itemId
        if (selectedItemId == android.R.id.home) {
            finish()
        } else if (selectedItemId == R.id.status_menu_item) {
            SchoolApp.data = assignmentList
            startActivity(Intent(this, AssignmentStatusActivity::class.java))
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        getAssignmentList()
    }

    private fun getAssignmentList() {
        SchoolApp.token?.let {
            db.collection("users").document(it).collection("primaryMenus")
                .document("assignment").collection("assignmentList").get()
                .addOnSuccessListener { queryDocumentSnapshots ->
                    assignmentList = mutableListOf()
                    if (!queryDocumentSnapshots.isEmpty) {
                        for (snapshots in queryDocumentSnapshots) {
                            val data: AssignmentModel =
                                snapshots.toObject(AssignmentModel::class.java)
                            assignmentList.add(data)
                        }
                        assignmentAdapter.updateData(assignmentList)
                    }
                }
        }
    }
}