package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

@Dao
interface OtherDAO {
    @Insert()
    fun insertOther(item: Other)
}