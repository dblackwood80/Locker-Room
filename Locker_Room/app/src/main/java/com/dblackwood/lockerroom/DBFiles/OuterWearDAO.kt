package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface OuterWearDAO {
    @Insert()
    fun insertOuter(item: OuterWear)

    @Query("SELECT * FROM outerwear")
    fun getAll(): List<OuterWear>

    @Query("SELECT * FROM outerwear WHERE locker_id = :ID")
    fun getByLocker(ID: Long): List<OuterWear>

    @Query("SELECT COUNT(*) FROM outerwear")
    fun getSize(): Long

    @Delete()
    fun deleteOuter(item: OuterWear)
}