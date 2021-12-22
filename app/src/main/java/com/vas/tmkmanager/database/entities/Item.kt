package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_item")
    var id: Int = 0

    var name: String

    var type: String

    var price: Double

    constructor(name: String, type: String, price: Double) {
        this.name = name
        this.type = type
        this.price = price
    }
}