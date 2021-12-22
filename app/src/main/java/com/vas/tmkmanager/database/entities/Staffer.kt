package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "staffer")
class Staffer {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "service_number")
    var serviceNumber: Int = 0

    @ColumnInfo(name = "full_name")
    var name: String

    @ColumnInfo(name = "phone_number")
    var phone: String

    var email: String

    @ColumnInfo(name = "position_category")
    var position: String

    constructor(name: String, phone: String, email: String, position: String) {
        this.name = name
        this.phone = phone
        this.email = email
        this.position = position
    }
}