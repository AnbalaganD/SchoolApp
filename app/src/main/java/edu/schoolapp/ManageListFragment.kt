package edu.schoolapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import edu.schoolapp.model.PrimaryMenuDao
import edu.schoolapp.SchoolApp.Companion.token
import java.util.Locale

class ManageListFragment : AppCompatActivity() {
    private var manageListAdapter: ManageListAdapter? = null
    private var manageList: MutableList<PrimaryMenuModel>? = null
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_manage_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db = FirebaseFirestore.getInstance()
        val manageListView = findViewById<ListView>(R.id.manage_list_view)
        manageListAdapter = ManageListAdapter(manageList)
        manageListView.adapter = manageListAdapter
        findViewById<View>(R.id.manage_button).setOnClickListener {
            val menuList = db.collection("users").document((token)!!).collection("primaryMenus")
            if (manageList != null) {
                for (menuModel: PrimaryMenuModel? in manageList!!) {
                    val dao = getPrimaryMenuDao(menuModel)
                    if (dao != null) {
                        menuList.document(dao.type!!.lowercase(Locale.getDefault())).set(dao)
                    }
                }
            }
            Toast.makeText(
                this@ManageListFragment,
                "Your list update successfully",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
        manageListView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
                val manageListModel: PrimaryMenuModel = manageList!![position]
                manageListModel.isSelected = !manageListModel.isSelected
                manageListAdapter!!.notifyDataSetChanged()
            }

        getPrimaryManageList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun getPrimaryManageList() {
        db.collection("users").document((token)!!).collection("primaryMenus").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                manageList = ArrayList()
                if (!queryDocumentSnapshots.isEmpty) {
                    for (snapshot: QueryDocumentSnapshot in queryDocumentSnapshots) {
                        val pMenu = snapshot.toObject(
                            PrimaryMenuDao::class.java
                        )
                        getPrimaryMenuModel(pMenu)?.let { manageList?.add(it) }
                    }
                    manageListAdapter!!.updateData(manageList)
                }
            }
    }

    private fun getPrimaryMenuModel(dao: PrimaryMenuDao): PrimaryMenuModel? {
        var primaryMenuModel: PrimaryMenuModel? = null
        when (dao.type) {
            "TODO" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.todo,
                PrimaryMenuType.TODO.toString(),
                PrimaryMenuType.TODO,
                dao.isEnable
            )

            "ASSIGNMENT" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.assignment,
                PrimaryMenuType.ASSIGNMENT.toString(),
                PrimaryMenuType.ASSIGNMENT,
                dao.isEnable
            )

            "WEBLINK" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.web_link,
                PrimaryMenuType.WEBLINK.toString(),
                PrimaryMenuType.WEBLINK,
                dao.isEnable
            )

            "MAIL" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.mail,
                PrimaryMenuType.MAIL.toString(),
                PrimaryMenuType.MAIL,
                dao.isEnable
            )

            "DRIVE" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.drive,
                PrimaryMenuType.DRIVE.toString(),
                PrimaryMenuType.DRIVE,
                dao.isEnable
            )

            "TIMETABLE" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.schedule,
                PrimaryMenuType.TIMETABLE.toString(),
                PrimaryMenuType.TIMETABLE,
                dao.isEnable
            )

            "CALENDAR" -> primaryMenuModel = PrimaryMenuModel(
                R.drawable.time_table,
                PrimaryMenuType.CALENDAR.toString(),
                PrimaryMenuType.CALENDAR,
                dao.isEnable
            )
        }
        return primaryMenuModel
    }

    private fun getPrimaryMenuDao(menuModel: PrimaryMenuModel?): PrimaryMenuDao? {
        var menuDao: PrimaryMenuDao? = null
        when (menuModel!!.menuType) {
            PrimaryMenuType.TODO -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "TODO", menuModel.isSelected)

            PrimaryMenuType.ASSIGNMENT -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "ASSIGNMENT", menuModel.isSelected)

            PrimaryMenuType.MAIL -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "MAIL", menuModel.isSelected)

            PrimaryMenuType.DRIVE -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "DRIVE", menuModel.isSelected)

            PrimaryMenuType.WEBLINK -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "WEBLINK", menuModel.isSelected)

            PrimaryMenuType.TIMETABLE -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "TIMETABLE", menuModel.isSelected)

            PrimaryMenuType.CALENDAR -> menuDao =
                PrimaryMenuDao(menuModel.menuName, "CALENDAR", menuModel.isSelected)

            else -> {}
        }
        return menuDao
    }
}