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

    @Delete()
    fun deleteAcce(lock: Locker)
}