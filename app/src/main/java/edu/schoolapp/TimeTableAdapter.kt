package edu.schoolapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TimeTableAdapter : RecyclerView.Adapter<TimeTableAdapter.TimeTableViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeTableViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TimeTableViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = 0

    inner class TimeTableViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    )
}