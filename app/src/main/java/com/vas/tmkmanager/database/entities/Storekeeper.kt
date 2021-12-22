package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "storekeeper", foreignKeys = [
    ForeignKey(
        entity = Storeroom::class,
        parentColumns = arrayOf("number_storeroom"),
        childColumns = arrayOf("number_storeroom")),
    ForeignKey(
        entity = Staffer::class,
        parentColumns = arrayOf("service_number"),
        childColumns = arrayOf("service_number"),
        onDelete = ForeignKey.CASCADE)]
)
class Storekeeper {
    @PrimaryKey()
    @ColumnInfo(name = "service_number")
    var serviceNumber: Int

    @ColumnInfo(name = "number_storeroom")
    var numberStoreroom: Int

    constructor(serviceNumber: Int, numberStoreroom: Int) {
        this.serviceNumber = serviceNumber
        this.numberStoreroom = numberStoreroom
    }
}