package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "storeroom")
class Storeroom {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "number_storeroom")
    var number: Int = 0

    @ColumnInfo(name = "address_storeroom")
    var address: String

    @ColumnInfo(name = "work_schedule")
    var workSchedule: String

    constructor(address: String, workSchedule: String) {
        this.address = address
        this.workSchedule = workSchedule
    }
}