package edu.schoolapp

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsIntent.SHARE_STATE_DEFAULT
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import edu.schoolapp.model.PrimaryMenuDao
import edu.schoolapp.SchoolApp.Companion.token
import java.util.Locale

class HomeActivity : AppCompatActivity(), PrimaryMenuSelectListener {
    private lateinit var primaryMenuAdapter: PrimaryMenuAdapter
    private var primaryMenuList: MutableList<PrimaryMenuModel>? = null
    private lateinit var db: FirebaseFirestore
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        db = FirebaseFirestore.getInstance()
        val primaryMenuRecyclerView = findViewById<RecyclerView>(R.id.primary_menu_recycler_view)
        primaryMenuRecyclerView.layoutManager = GridLayoutManager(this, 3)
        primaryMenuAdapter = PrimaryMenuAdapter(primaryMenuList, this)
        primaryMenuRecyclerView.adapter = primaryMenuAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selectedItemId = item.itemId
        if (selectedItemId == R.id.notification_menu_item) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        getPrimaryMenu()
    }

    private fun setupPrimaryMenu(queryDocumentSnapshot: QuerySnapshot?) {
        primaryMenuList = mutableListOf()
        if (queryDocumentSnapshot == null) {
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.todo,
                    PrimaryMenuType.TODO.toString(),
                    PrimaryMenuType.TODO
                )
            )
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.assignment,
                    PrimaryMenuType.ASSIGNMENT.toString(),
                    PrimaryMenuType.ASSIGNMENT
                )
            )
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.web_link,
                    PrimaryMenuType.WEBLINK.toString(),
                    PrimaryMenuType.WEBLINK
                )
            )
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.mail,
                    PrimaryMenuType.MAIL.toString(),
                    PrimaryMenuType.MAIL
                )
            )
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.drive,
                    PrimaryMenuType.DRIVE.toString(),
                    PrimaryMenuType.DRIVE
                )
            )
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.schedule,
                    PrimaryMenuType.TIMETABLE.toString(),
                    PrimaryMenuType.TIMETABLE
                )
            )
            primaryMenuList?.add(
                PrimaryMenuModel(
                    R.drawable.time_table,
                    PrimaryMenuType.CALENDAR.toString(),
                    PrimaryMenuType.CALENDAR
                )
            )
        } else {
            for (snapshot in queryDocumentSnapshot) {
                val pMenu = snapshot.toObject(PrimaryMenuDao::class.java)
                if (pMenu.isEnable) getPrimaryMenuModel(pMenu)?.let { primaryMenuList?.add(it) }
            }
        }
        primaryMenuAdapter.updateData(primaryMenuList)
    }

    override fun onMenuSelect(menuModel: PrimaryMenuModel?) {
        //TODO implement your select logic here
        if (menuModel?.menuType === PrimaryMenuType.TODO) {
            startActivity(Intent(this, TodoFragment::class.java))
        } else if (menuModel?.menuType === PrimaryMenuType.ASSIGNMENT) {
            startActivity(Intent(this, AssignmentActivity::class.java))
        } else if (menuModel?.menuType === PrimaryMenuType.WEBLINK) {
            openWebLink()
        } else if (menuModel?.menuType === PrimaryMenuType.MAIL) {
            openGmailApp()
        } else if (menuModel?.menuType === PrimaryMenuType.DRIVE) {
        } else if (menuModel?.menuType === PrimaryMenuType.TIMETABLE) {
        } else if (menuModel?.menuType === PrimaryMenuType.CALENDAR) {
        }
    }

    private fun openWebLink() {
        val url = "https://www.google.com/"
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShareState(SHARE_STATE_DEFAULT)
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(
                        ContextCompat.getColor(
                            this@HomeActivity,
                            R.color.colorPrimary
                        )
                    )
                    .build()
            )
            .setShowTitle(true)
            .build()
        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    private fun openGmailApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        val pm = packageManager
        val matches = pm.queryIntentActivities(intent, 0)
        var best: ResolveInfo? = null
        for (info in matches) if (info.activityInfo.packageName.endsWith(".gm") ||
            info.activityInfo.name.lowercase(Locale.getDefault()).contains("gmail")
        ) best = info
        if (best != null) intent.setClassName(best.activityInfo.packageName, best.activityInfo.name)
        startActivity(intent)
    }

    private fun getPrimaryMenu() {
        db.collection("users").document(token!!).collection("primaryMenus").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                if (queryDocumentSnapshots.isEmpty) {
                    pushDefaultPrimaryMenuIntoDatabase()
                    setupPrimaryMenu(null)
                } else setupPrimaryMenu(queryDocumentSnapshots)
            }
    }

    private fun pushDefaultPrimaryMenuIntoDatabase() {
        val primaryMenus = db.collection("users").document(token!!).collection("primaryMenus")
        primaryMenus.document("todo").set(PrimaryMenuDao("Todo", "TODO", true))
        primaryMenus.document("assignment").set(PrimaryMenuDao("Assignment", "ASSIGNMENT", true))
        primaryMenus.document("weblink").set(PrimaryMenuDao("WebLink", "WEBLINK", true))
        primaryMenus.document("mail").set(PrimaryMenuDao("Mail", "MAIL", true))
        primaryMenus.document("drive").set(PrimaryMenuDao("Drive", "DRIVE", true))
        primaryMenus.document("timetable").set(PrimaryMenuDao("Timetable", "TIMETABLE", true))
        primaryMenus.document("calendar").set(PrimaryMenuDao("Calendar", "CALENDAR", true))
    }

    private fun getPrimaryMenuModel(dao: PrimaryMenuDao): PrimaryMenuModel? {
        var primaryMenuModel: PrimaryMenuModel? = null
        when (dao.type) {
            "TODO" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.todo, "TODO", PrimaryMenuType.TODO)

            "ASSIGNMENT" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.assignment, "ASSIGNMENT", PrimaryMenuType.ASSIGNMENT)

            "WEBLINK" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.web_link, "WEBLINK", PrimaryMenuType.WEBLINK)

            "MAIL" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.mail, "MAIL", PrimaryMenuType.MAIL)

            "DRIVE" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.drive, "DRIVE", PrimaryMenuType.DRIVE)

            "TIMETABLE" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.schedule, "TIMETABLE", PrimaryMenuType.TIMETABLE)

            "CALENDAR" -> primaryMenuModel =
                PrimaryMenuModel(R.drawable.time_table, "CALENDAR", PrimaryMenuType.CALENDAR)
        }
        return primaryMenuModel
    }
}