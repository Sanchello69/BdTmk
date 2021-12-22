package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "order_tmk", foreignKeys = [
    ForeignKey(
        entity = Client::class,
        parentColumns = arrayOf("id_client"),
        childColumns = arrayOf("id_client"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("id_item"),
        childColumns = arrayOf("id_item"),
        onDelete = ForeignKey.NO_ACTION)],
    primaryKeys = ["id_order", "id_client", "id_item"]
)
class OrderTMK {
    @ColumnInfo(name = "id_client")
    var idClient: Int

    @ColumnInfo(name = "id_order")
    var idOrder: Int

    @ColumnInfo(name = "id_item")
    var idItem: Int

    @ColumnInfo(name = "data_order")
    var dataOrder: String

    @ColumnInfo(name = "data_pay")
    var dataPay: String

    @ColumnInfo(name = "data_delivery")
    var dataDelivery: String

    @ColumnInfo(name = "status_order")
    var status: String

    @ColumnInfo(name = "sum_order")
    var sum: Double

    var amount: Int

    constructor(
        idOrder: Int,
        idClient: Int,
        idItem: Int,
        dataOrder: String,
        dataPay: String,
        dataDelivery: String,
        status: String,
        sum: Double,
        amount: Int
    ) {
        this.idClient = idClient
        this.idOrder = idOrder
        this.idItem = idItem
        this.dataOrder = dataOrder
        this.dataPay = dataPay
        this.dataDelivery = dataDelivery
        this.status = status
        this.sum = sum
        this.amount = amount
    }
}