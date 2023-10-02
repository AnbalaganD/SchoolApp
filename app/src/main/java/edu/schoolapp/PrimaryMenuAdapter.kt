package edu.schoolapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.schoolapp.PrimaryMenuAdapter.PrimaryMenuViewHolder

class PrimaryMenuAdapter(
    private var primaryMenuList: MutableList<PrimaryMenuModel>?,
    private val listener: PrimaryMenuSelectListener
) : RecyclerView.Adapter<PrimaryMenuViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrimaryMenuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_primary_menu, parent, false)
        return PrimaryMenuViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: PrimaryMenuViewHolder, position: Int) {
        holder.setupData(primaryMenuList!![position])
    }

    override fun getItemCount(): Int {
        return if (primaryMenuList == null) 0 else primaryMenuList!!.size
    }

    fun updateData(list: List<PrimaryMenuModel>?) {
        if (primaryMenuList == null) {
            primaryMenuList = ArrayList()
        } else {
            primaryMenuList!!.clear()
        }
        primaryMenuList!!.addAll(list!!)
        notifyDataSetChanged()
    }

    inner class PrimaryMenuViewHolder(
        itemView: View,
        private val listener: PrimaryMenuSelectListener
    ) : RecyclerView.ViewHolder(itemView) {
        private var data: PrimaryMenuModel? = null
        private val menuImage: ImageView = itemView.findViewById(R.id.menu_image_view)
        private val menuTitle: TextView = itemView.findViewById(R.id.menu_title)

        init {
            itemView.setOnClickListener { listener.onMenuSelect(data) }
        }

        fun setupData(data: PrimaryMenuModel) {
            this.data = data
            menuImage.setImageResource(data.menuImageResource)
            menuTitle.text = data.menuName
        }
    }
}