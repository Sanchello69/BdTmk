package com.vas.tmkmanager.tmkmain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.entities.Storeroom

class ItemViewStoreroomHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val infoImage: ImageView = itemView.findViewById(R.id.imageViewInfo)
    private val address: TextView = itemView.findViewById(R.id.textView3)
    private val workHour: TextView = itemView.findViewById(R.id.textViewWork)

    fun bind(item: Storeroom, storeroomClickListener: StoreroomAdapter.OnStoreroomClickListener) {
        address.text = item.address
        workHour.text = item.workSchedule

        infoImage.setOnClickListener {
            storeroomClickListener.onInfoClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewStoreroomHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.storeroom_item_view, parent, false)
            return ItemViewStoreroomHolder(view)
        }
    }
}

class StoreroomAdapter : RecyclerView.Adapter<ItemViewStoreroomHolder>() {

    var data = listOf<Storeroom>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: OnStoreroomClickListener? = null

    interface OnStoreroomClickListener {
        fun onInfoClick(storeroom: Storeroom?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewStoreroomHolder {
        return ItemViewStoreroomHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewStoreroomHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener!!)
    }

    override fun getItemCount() = data.size
}