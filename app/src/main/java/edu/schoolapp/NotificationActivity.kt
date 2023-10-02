package edu.schoolapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationActivity : AppCompatActivity() {
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var notificationList: MutableList<NotificationModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.close)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        val notificationRecyclerView = findViewById<RecyclerView>(R.id.notification_recycler_view)
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)
        notificationList = mutableListOf()
        addDummyData()
        notificationAdapter = NotificationAdapter(notificationList)
        notificationRecyclerView.adapter = notificationAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    private fun getNotification() {
        //TODO implement api for get remote notification
    }

    private fun addDummyData() {
        val item1 = NotificationModel("Nice, This is test notification", "just now")
        val item2 = NotificationModel("Nice, This is test notification", "just now")
        val item3 = NotificationModel("Nice, This is test notification", "just now")
        val item4 = NotificationModel("Nice, This is test notification", "just now")
        val item5 = NotificationModel("Nice, This is test notification", "just now")
        notificationList.add(item1)
        notificationList.add(item2)
        notificationList.add(item3)
        notificationList.add(item4)
        notificationList.add(item5)
    }
}