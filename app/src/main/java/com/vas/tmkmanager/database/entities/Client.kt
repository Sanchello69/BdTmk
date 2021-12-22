package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client")
class Client {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_client")
    var id: Int = 0

    @ColumnInfo(name = "name_client")
    var name: String

    @ColumnInfo(name = "phone_client")
    var phone: String

    @ColumnInfo(name = "address_client")
    var address: String

    @ColumnInfo(name = "type_client")
    var type: String

    constructor(name: String, phone: String, address: String, type: String) {
        this.name = name
        this.phone = phone
        this.address = address
        this.type = type
    }
}