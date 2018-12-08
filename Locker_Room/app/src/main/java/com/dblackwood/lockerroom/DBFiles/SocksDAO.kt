package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface SocksDAO {
    @Insert()
    fun insertSock(item: Socks)

    @Query("SELECT * FROM socks")
    fun getAll(): List<Socks>

    @Query("SELECT * FROM socks WHERE locker_id = :ID")
    fun getByLocker(ID: Long): List<Socks>

    @Query("SELECT COUNT(*) FROM socks")
    fun getSize(): Long

    @Delete()
    fun deleteSock(item: Socks)
}