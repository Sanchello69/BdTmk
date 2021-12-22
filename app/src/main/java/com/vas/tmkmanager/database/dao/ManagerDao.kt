package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Manager

@Dao
interface ManagerDao : BaseDao<Manager> {

    @Query("SELECT * FROM manager WHERE service_number = :key") //получение записи по id
    fun get(key: Int): LiveData<Manager?>
}