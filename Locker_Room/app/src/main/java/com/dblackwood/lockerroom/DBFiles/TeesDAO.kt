package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface TeesDAO {
    @Insert()
    fun insertTee(item: Tees)

    @Query("SELECT * FROM tees")
    fun getAll(): List<Tees>

    @Query("SELECT COUNT(*) FROM tees")
    fun getSize(): Long

    @Delete()
    fun deleteTee(item: Tees)
}