package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "item_storeroom", foreignKeys = [
    ForeignKey(
        entity = Storeroom::class,
        parentColumns = arrayOf("number_storeroom"),
        childColumns = arrayOf("number_storeroom"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(
        entity = Item::class,
        parentColumns = arrayOf("id_item"),
        childColumns = arrayOf("id_item"),
        onDelete = ForeignKey.CASCADE)],
    primaryKeys = ["id_item", "number_storeroom"]
)
class ItemStoreroom {
    @ColumnInfo(name = "id_item")
    var idItem: Int

    @ColumnInfo(name = "number_storeroom")
    var number: Int

    @ColumnInfo(name = "quanity_storeroom")
    var quanity: Int

    constructor(idItem: Int, number: Int, quanity: Int) {
        this.idItem = idItem
        this.number = number
        this.quanity = quanity
    }
}