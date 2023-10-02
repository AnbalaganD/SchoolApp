package edu.schoolapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class ManageListAdapter(private var manageList: MutableList<PrimaryMenuModel>?) : BaseAdapter() {
    override fun getCount(): Int {
        return if (manageList == null) 0 else manageList!!.size
    }

    override fun getItem(position: Int): Any {
        return manageList!![position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_manage_list, parent, false)
            val viewHolder = ManageListViewHolder(convertView)
            convertView.tag = viewHolder
            viewHolder.setupData(getItem(position) as PrimaryMenuModel)
            return convertView
        }
        (convertView.tag as ManageListViewHolder).setupData(getItem(position) as PrimaryMenuModel)
        return convertView
    }

    fun updateData(list: List<PrimaryMenuModel>?) {
        if (manageList == null) {
            manageList = ArrayList()
        } else {
            manageList!!.clear()
        }
        manageList!!.addAll(list!!)
        notifyDataSetChanged()
    }

    internal class ManageListViewHolder(view: View) {
        private var manageListImageView: ImageView = view.findViewById(R.id.list_image_view)
        private var manageListTitle: TextView = view.findViewById(R.id.list_title_text_view)
        private var addOrRemoveImageView: ImageView = view.findViewById(R.id.add_or_remove_image_view)

        fun setupData(data: PrimaryMenuModel) {
            manageListImageView.setImageResource(data.menuImageResource)
            manageListTitle.text = data.menuName
            addOrRemoveImageView.setImageResource(if (data.isSelected) R.drawable.minus else R.drawable.plus)
        }
    }
}