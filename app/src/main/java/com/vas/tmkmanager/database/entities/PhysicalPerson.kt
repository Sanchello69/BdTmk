package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "physical_person", foreignKeys = [ForeignKey(
    entity = Client::class,
    parentColumns = arrayOf("id_client"),
    childColumns = arrayOf("id_client"),
    onDelete = ForeignKey.CASCADE)]
)
class PhysicalPerson {
    @PrimaryKey()
    @ColumnInfo(name = "id_client")
    var id: Int

    var passport: String

    constructor(id: Int, passport: String) {
        this.id = id
        this.passport = passport
    }
}