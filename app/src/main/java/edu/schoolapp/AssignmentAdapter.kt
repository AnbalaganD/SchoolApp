package edu.schoolapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.schoolapp.AssignmentAdapter.AssignmentVH
import edu.schoolapp.model.AssignmentModel

class AssignmentAdapter(private var assignmentModelList: MutableList<AssignmentModel>?) :
    RecyclerView.Adapter<AssignmentVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentVH {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_assignment, parent, false)
        return AssignmentVH(view)
    }

    override fun onBindViewHolder(holder: AssignmentVH, position: Int) {
        holder.setupData(assignmentModelList!![position])
    }

    override fun getItemCount(): Int {
        return if (assignmentModelList == null) 0 else assignmentModelList!!.size
    }

    fun updateData(list: List<AssignmentModel>?) {
        if (assignmentModelList == null) {
            assignmentModelList = ArrayList()
        }
        assignmentModelList!!.clear()
        assignmentModelList!!.addAll(list!!)
        notifyDataSetChanged()
    }

    class AssignmentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title_text_view)
        private val unitTextView: TextView = itemView.findViewById(R.id.subject_text_view)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.description_text_view)
        private val markTextView: TextView = itemView.findViewById(R.id.mark_text_view)

        fun setupData(item: AssignmentModel) {
            titleTextView.text = item.title
            unitTextView.text = item.unit
            descriptionTextView.text = item.description
            markTextView.text = String.format("%d out of %d", item.markObtained, item.mark)
        }
    }
}