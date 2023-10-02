package edu.schoolapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.schoolapp.NotificationAdapter.NotificationViewHolder

class NotificationAdapter(private val notificationList: List<NotificationModel>?) :
    RecyclerView.Adapter<NotificationViewHolder>() {
    override fun getItemCount(): Int {
        return notificationList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.setupData(notificationList!![position])
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contentTextView: TextView = itemView.findViewById(R.id.notification_content_text_view)
        private val notificationTimeTextView: TextView = itemView.findViewById(R.id.time_text_view)

        fun setupData(item: NotificationModel) {
            contentTextView.text = item.content
            notificationTimeTextView.text = item.notificationTime
        }
    }
}