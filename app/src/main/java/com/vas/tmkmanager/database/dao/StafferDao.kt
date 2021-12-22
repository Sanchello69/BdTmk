package com.vas.tmkmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.vas.tmkmanager.database.entities.Staffer

@Dao
interface StafferDao : BaseDao<Staffer> {

    @Query("SELECT * FROM staffer WHERE service_number = :key") //получение записи по id
    fun get(key: Int): LiveData<Staffer?>

    @Query("SELECT * FROM staffer WHERE service_number = :key and full_name = :name and position_category = \"Менеджер\"") //проверка для входа
    fun getСheck(key: Int, name: String): Staffer?

    @Query("SELECT * FROM staffer ORDER BY full_name") //получение всех записей
    fun getAll(): LiveData<List<Staffer>?>

    @Query("SELECT * FROM staffer ORDER BY full_name DESC LIMIT 1")
    fun getStaffer(): Staffer?

    @Query("DELETE FROM staffer WHERE service_number = :id")
    fun deleteStaffer(id: Int)
}