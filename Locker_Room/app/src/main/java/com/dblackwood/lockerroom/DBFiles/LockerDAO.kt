package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.*

@Dao
interface LockerDAO {
    @Insert()
    fun insertLocker(lock: Locker)

    @Query("SELECT * FROM locker")
    fun getAll(): List<Locker>

    @Query("SELECT COUNT(*) FROM locker")
    fun getSize(): Long

    @Query("SELECT locker_name FROM locker WHERE locker_name = :name")
    fun checkLockerExist(name: String): List<LockNameVar>?

    @Query("SELECT * FROM locker WHERE locker_name = :name")
    fun getID(name: String): Int

    @Delete()
    fun deleteLocker(lock: Locker)
}

data class LockNameVar(
        @ColumnInfo(name = "locker_name") var lckName: String?
)