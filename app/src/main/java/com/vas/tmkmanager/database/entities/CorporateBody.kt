package com.vas.tmkmanager.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (tableName = "corporate_body", foreignKeys = [ForeignKey(
    entity = Client::class,
    parentColumns = arrayOf("id_client"),
    childColumns = arrayOf("id_client"),
    onDelete = ForeignKey.CASCADE)]
)
class CorporateBody {
    @PrimaryKey()
    @ColumnInfo(name = "id_client")
    var id: Int

    @ColumnInfo(name = "payment_account")
    var payAccount: String

    constructor(id: Int, payAccount: String) {
        this.id = id
        this.payAccount = payAccount
    }
}