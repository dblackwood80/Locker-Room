package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface OtherDAO {
    @Insert()
    fun insertOther(item: Other)

    @Query("SELECT * FROM other")
    fun getAll(): List<Other>

    @Query("SELECT * FROM other WHERE locker_id = :ID")
    fun getByLocker(ID: Long): List<Other>

    @Query("SELECT COUNT(*) FROM other")
    fun getSize(): Long

    @Delete()
    fun deleteOther(item: Other)
}