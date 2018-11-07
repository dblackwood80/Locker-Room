package com.dblackwood.lockerroom.DBFiles

import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import java.text.DecimalFormat

interface AccessoriesDAO {
    @Insert()
    fun insertAcce(lockID: Int, acces_ID: Int, prce: DecimalFormat, brd: String, qnty: Int, des: String, clur: String, siz: String)

    @Query("DELETE FROM accessories where locker_id = :l and acce_id = :a")
    fun deleteAcce(l: Int, a: Int)
}