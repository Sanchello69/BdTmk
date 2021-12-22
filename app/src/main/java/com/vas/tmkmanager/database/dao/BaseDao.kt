package com.vas.tmkmanager.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //вставка объекта в БД
    fun insert(obj: T)

    @Insert //вставка списка объектов в БД
    fun insert(vararg obj: T)

    @Insert
    fun insert(obj: List<T>)

    @Update //обновление объекта в БД
    fun update(obj: T)

    @Delete //удаление объекта в БД
    fun delete(obj: T)

}