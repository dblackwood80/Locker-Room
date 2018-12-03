package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface PantsDAO {
    @Insert()
    fun insertPant(item: Pants)

    @Query("SELECT * FROM pants")
    fun getAll(): List<Pants>

    @Query("SELECT COUNT(*) FROM pants")
    fun getSize(): Long

    @Delete()
    fun deletePant(item: Pants)
}