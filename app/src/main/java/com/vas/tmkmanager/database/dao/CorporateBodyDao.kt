package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.CorporateBody

@Dao
interface CorporateBodyDao : BaseDao<CorporateBody> {

    @Query("SELECT * FROM corporate_body WHERE id_client = :key") //получение записи по id
    fun get(key: Int): LiveData<CorporateBody?>

    @Query("SELECT * FROM corporate_body ORDER BY id_client") //получение всех записей
    fun getAll(): LiveData<List<CorporateBody>?>

    @Query("SELECT * FROM corporate_body ORDER BY id_client DESC LIMIT 1")
    fun getStaffer(): CorporateBody?
}