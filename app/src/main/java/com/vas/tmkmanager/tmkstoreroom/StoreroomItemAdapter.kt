package com.vas.tmkmanager.tmkstoreroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vas.tmkmanager.R

class ItemViewStoreroomItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val editImage: ImageView = itemView.findViewById(R.id.imageViewEditStoreroom)
    private val quanity: TextView = itemView.findViewById(R.id.textViewQuanity)
    private val name: TextView = itemView.findViewById(R.id.textViewNameItemStore)

    fun bind(item: ItemStoreroomForList, storeroomClickListener: StoreroomItemAdapter.OnStoreroomItemClickListener) {
        quanity.text = item.quanity_storeroom.toString()
        name.text = item.name

        editImage.setOnClickListener {
            storeroomClickListener.onEditClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewStoreroomItemHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.quanity_storeroom_item, parent, false)
            return ItemViewStoreroomItemHolder(view)
        }
    }
}

class StoreroomItemAdapter : RecyclerView.Adapter<ItemViewStoreroomItemHolder>() {

    var data = listOf<ItemStoreroomForList>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: OnStoreroomItemClickListener? = null

    interface OnStoreroomItemClickListener {
        fun onEditClick(storeroomItem: ItemStoreroomForList?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewStoreroomItemHolder {
        return ItemViewStoreroomItemHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewStoreroomItemHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener!!)
    }

    override fun getItemCount() = data.size
}