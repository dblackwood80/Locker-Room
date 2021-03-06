package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import java.text.DecimalFormat

@Dao
interface AccessoriesDAO {
    @Insert()
    fun insertAcce(item: Accessories)

    @Query("SELECT * FROM accessories")
    fun getAll(): List<Accessories>

    @Query("SELECT * FROM accessories WHERE locker_id = :ID")
    fun getByLocker(ID: Long): List<Accessories>

    @Query("SELECT COUNT(*) FROM accessories")
    fun getSize(): Long

    @Delete()
    fun deleteAcce(item: Accessories)
}