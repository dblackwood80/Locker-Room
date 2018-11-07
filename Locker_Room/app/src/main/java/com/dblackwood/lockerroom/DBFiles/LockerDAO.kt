package com.dblackwood.lockerroom.DBFiles

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import java.text.DecimalFormat

interface LockerDAO {
    @Insert()
    fun insertLocker(lockID: Int, name: String)

    @Query("DELETE FROM accessories where locker_id = :l")
    fun deleteLocker(l: Int)
}