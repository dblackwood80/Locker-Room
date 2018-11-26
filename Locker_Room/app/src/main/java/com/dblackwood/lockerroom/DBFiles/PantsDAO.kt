package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert

@Dao
interface PantsDAO {
    @Insert()
    fun insertPant(item: Pants)
}