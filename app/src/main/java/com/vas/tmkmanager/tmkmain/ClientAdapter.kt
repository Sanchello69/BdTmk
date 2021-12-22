package com.vas.tmkmanager.tmkmain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.entities.Client

class ItemViewClientHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val deleteImage: ImageView = itemView.findViewById(R.id.imageViewDelete)
    private val editImage: ImageView = itemView.findViewById(R.id.imageViewEditStoreroom)
    private val infoImage: ImageView = itemView.findViewById(R.id.imageViewInfo)
    private val nameFull: TextView = itemView.findViewById(R.id.textView3)
    private val category: TextView = itemView.findViewById(R.id.textViewWork)

    fun bind(item: Client, clientClickListener: ClientAdapter.OnClientClickListener) {
        nameFull.text = item.name.toString()
        if (item.type == "Физ")
            category.text = "Физическое лицо"
        else
            category.text = "Юридическое лицо"


        deleteImage.setOnClickListener {
            clientClickListener.onDeleteClick(item)
        }
        editImage.setOnClickListener {
            clientClickListener.onEditClick(item)
        }
        infoImage.setOnClickListener {
            clientClickListener.onInfoClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewClientHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.client_item_view, parent, false)
            return ItemViewClientHolder(view)
        }
    }
}

class ClientAdapter : RecyclerView.Adapter<ItemViewClientHolder>() {

    var data = listOf<Client>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: OnClientClickListener? = null

    interface OnClientClickListener {
        fun onInfoClick(client: Client?)
        fun onEditClick(client: Client?)
        fun onDeleteClick(client: Client?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewClientHolder {
        return ItemViewClientHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewClientHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener!!)
    }

    override fun getItemCount() = data.size
}