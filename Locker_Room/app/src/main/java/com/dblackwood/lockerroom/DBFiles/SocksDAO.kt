package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

@Dao
interface SocksDAO {
    @Insert()
    fun insertSock(item: Socks)
}