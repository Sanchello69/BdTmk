package com.vas.tmkmanager.tmkclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vas.tmkmanager.R
import com.vas.tmkmanager.database.entities.OrderTMK

class ItemViewOrderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val deleteImage: ImageView = itemView.findViewById(R.id.imageViewDeleteOrder)
    private val editImage: ImageView = itemView.findViewById(R.id.imageViewEditOrder)
    private val dataOrder: TextView = itemView.findViewById(R.id.textViewDataOrder)
    private val dataPay: TextView = itemView.findViewById(R.id.textViewDataPay)
    private val dataDelivery: TextView = itemView.findViewById(R.id.textViewDataDelivery)
    private val status: TextView = itemView.findViewById(R.id.textViewStatusOrder)
    private val sum: TextView = itemView.findViewById(R.id.textViewSum)
    private val amount: TextView = itemView.findViewById(R.id.textViewAmount)
    private val idItem: TextView = itemView.findViewById(R.id.textViewIdItem)

    fun bind(item: OrderTMK, orderClickListener: OrderAdapter.OnOrderClickListener) {
        dataOrder.text = "Дата заказа: " + item.dataOrder.toString()
        dataPay.text = "Дата оплаты: " + item.dataPay.toString()
        dataDelivery.text = "Дата доставки: " + item.dataDelivery.toString()
        status.text = "Статус заказа: " + item.status.toString()
        sum.text = item.sum.toString() + " руб."
        amount.text = "(" + item.amount.toString() + ")"
        idItem.text = "ID товара: " + item.idItem.toString()


        deleteImage.setOnClickListener {
            orderClickListener.onDeleteClick(item)
        }
        editImage.setOnClickListener {
            orderClickListener.onEditClick(item)
        }
    }

    companion object {
        fun from(parent: ViewGroup): ItemViewOrderHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater
                .inflate(R.layout.order_item_view, parent, false)
            return ItemViewOrderHolder(view)
        }
    }
}

class OrderAdapter : RecyclerView.Adapter<ItemViewOrderHolder>() {

    var data = listOf<OrderTMK>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onClickListener: OnOrderClickListener? = null

    interface OnOrderClickListener {
        fun onEditClick(order: OrderTMK?)
        fun onDeleteClick(order: OrderTMK?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewOrderHolder {
        return ItemViewOrderHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewOrderHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onClickListener!!)
    }

    override fun getItemCount() = data.size
}