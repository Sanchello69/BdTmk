package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "manager", foreignKeys = [ForeignKey(
    entity = Staffer::class,
    parentColumns = arrayOf("service_number"),
    childColumns = arrayOf("service_number"),
    onDelete = ForeignKey.CASCADE)]
)
class Manager {
    @PrimaryKey()
    @ColumnInfo(name = "service_number")
    var serviceNumber: Int

    @ColumnInfo(name = "office_number")
    var officeNumber: Int

    constructor(serviceNumber: Int, officeNumber: Int) {
        this.serviceNumber = serviceNumber
        this.officeNumber = officeNumber
    }
}