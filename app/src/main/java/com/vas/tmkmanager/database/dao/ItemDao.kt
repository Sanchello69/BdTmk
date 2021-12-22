package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Client
import com.vas.tmkmanager.database.entities.Item

@Dao
interface ItemDao : BaseDao<Item> {

    @Query("SELECT * FROM item WHERE id_item = :key") //получение записи по id
    fun get(key: Int): LiveData<Item?>

    @Query("SELECT * FROM item") //получение всех записей
    fun getAll(): LiveData<List<Item>?>

    @Query("SELECT name FROM item") //получение всех названий
    fun getAllName(): LiveData<List<String>?>
}