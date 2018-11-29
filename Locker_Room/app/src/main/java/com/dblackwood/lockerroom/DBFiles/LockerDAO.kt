package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.*

@Dao
interface LockerDAO {
    @Insert()
    fun insertLocker(lock: Locker)

    @Query("SELECT * FROM locker")
    fun getAll(): List<Locker>

    @Query("SELECT locker_name FROM locker WHERE locker_name = :name")
    fun checkLockerExist(name: String): List<LockNameVar>?

    @Delete()
    fun deleteLocker(lock: Locker)
}

data class LockNameVar(
        @ColumnInfo(name = "locker_name") var lckName: String?
)