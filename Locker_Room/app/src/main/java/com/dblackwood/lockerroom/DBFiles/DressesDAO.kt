package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface DressesDAO {
    @Insert()
    fun insertDress(item: Dresses)

    @Query("SELECT * FROM dresses")
    fun getAll(): List<Dresses>

    @Query("SELECT COUNT(*) FROM dresses")
    fun getSize(): Long

    @Delete()
    fun deleteDress(item: Dresses)
}