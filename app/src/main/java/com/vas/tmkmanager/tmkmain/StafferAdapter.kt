package com.vas.tmkmanager.tmkmain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.entities.Staffer

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val deleteImage: ImageView = itemView.findViewById(R.id.imageViewDelete)
    private val infoImage: ImageView = itemView.findViewById(R.id.imageViewInfo)
    private val nameFull: TextView = itemView.findViewById(R.id.textView3)
    private val category: TextView = itemView.findViewById(R.id.textViewWork)

    fun bind(item: Staffer, stafferClickListener: StafferAdapter.OnStafferClickListener) {
        nameFull.text = item.name.toString()
        category.text = item.position.toString()

        deleteImage.setOnClickListener {
            stafferClickListener.onDeleteClick(item)
        }
        infoImage.setOnClickListener {
            stafferClickListener.onInfoClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.staffer_item_view, parent, false)
            return ItemViewHolder(view)
        }
    }
}

class StafferAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    var data = listOf<Staffer>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: OnStafferClickListener? = null

    interface OnStafferClickListener {
        fun onInfoClick(staffer: Staffer?)
        fun onDeleteClick(staffer: Staffer?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener!!)
    }

    override fun getItemCount() = data.size
        /*override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }*/
}