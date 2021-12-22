package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "selling", foreignKeys = [
    ForeignKey(
        entity = Staffer::class,
        parentColumns = arrayOf("service_number"),
        childColumns = arrayOf("service_number"),
    onDelete = ForeignKey.CASCADE),

    ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("id_item"),
        childColumns = arrayOf("id_item"))],
    primaryKeys = ["id_selling", "id_item", "service_number"]
)
class Selling {
    @ColumnInfo(name = "id_selling")
    var idSelling: Int

    @ColumnInfo(name = "id_item")
    var idItem: Int

    @ColumnInfo(name = "service_number")
    var serviceNumber: Int

    @ColumnInfo(name = "selling_data")
    var data: String

    @ColumnInfo(name = "selling_sum")
    var sum: Double

    constructor(idSelling: Int, idItem: Int, serviceNumber: Int, data: String, sum: Double) {
        this.idSelling = idSelling
        this.idItem = idItem
        this.serviceNumber = serviceNumber
        this.data = data
        this.sum = sum
    }
}