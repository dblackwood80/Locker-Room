package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface LockerDAO {
    @Insert()
    fun insertLocker(lock: Locker)

    @Delete()
    fun deleteLocker(lock: Locker)
}