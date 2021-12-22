package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Selling

@Dao
interface SellingDao : BaseDao<Selling> {

    @Query("SELECT * FROM selling WHERE id_selling = :key") //получение записи по id
    fun get(key: Int): LiveData<Selling?>
}