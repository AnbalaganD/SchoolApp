package edu.schoolapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import edu.schoolapp.model.TodoModel

class TodoAdapter internal constructor(private var todoList: MutableList<TodoModel>?) :
    BaseAdapter() {
    override fun getCount(): Int {
        return if (todoList == null) 0 else todoList!!.size
    }

    override fun getItem(position: Int): Any {
        return todoList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(parent?.context).inflate(R.layout.item_todo, parent, false)
            val viewHolder = TodoViewHolder(convertView)
            convertView.tag = viewHolder
            viewHolder.setupData(getItem(position) as TodoModel)
            return convertView
        }
        (convertView.tag as TodoViewHolder).setupData(getItem(position) as TodoModel)
        return convertView
    }

    fun updateData(list: List<TodoModel>?) {
        if (todoList == null) {
            todoList = ArrayList()
        } else {
            todoList!!.clear()
        }
        todoList!!.addAll(list!!)
        notifyDataSetChanged()
    }

    internal class TodoViewHolder(view: View) {
        private var titleTextView: TextView = view.findViewById(R.id.title_text_view)
        private var descriptionTextView: TextView = view.findViewById(R.id.description_text_view)
        private var dateTextView: TextView = view.findViewById(R.id.date_text_view)

        fun setupData(data: TodoModel) {
            //TODO setup data here
            titleTextView.text = data.title
            descriptionTextView.text = data.description
            dateTextView.text = data.date
        }
    }
}