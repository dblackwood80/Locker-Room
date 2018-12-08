package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ShoesDAO {
    @Insert()
    fun insertShoe(item: Shoes)

    @Query("SELECT * FROM shoes")
    fun getAll(): List<Shoes>

    @Query("SELECT * FROM shoes WHERE locker_id = :ID")
    fun getByLocker(ID: Long): List<Shoes>

    @Query("SELECT COUNT(*) FROM shoes")
    fun getSize(): Long

    @Delete()
    fun deleteShoe(item: Shoes)
}