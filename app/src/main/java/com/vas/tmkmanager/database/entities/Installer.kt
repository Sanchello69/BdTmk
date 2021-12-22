package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "installer", foreignKeys = [ForeignKey(
    entity = Staffer::class,
    parentColumns = arrayOf("service_number"),
    childColumns = arrayOf("service_number"),
onDelete = ForeignKey.CASCADE)]
)
class Installer {
    @PrimaryKey()
    @ColumnInfo(name = "service_number")
    var serviceNumber: Int

    var location: String

    constructor(serviceNumber: Int, location: String) {
        this.serviceNumber = serviceNumber
        this.location = location
    }
}