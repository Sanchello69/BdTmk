package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Storekeeper

@Dao
interface StorekeeperDao : BaseDao<Storekeeper> {

    @Query("SELECT * FROM storekeeper WHERE service_number = :key") //получение записи по id
    fun get(key: Int): LiveData<Storekeeper?>
}