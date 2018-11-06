package com.dblackwood.lockerroom.DBFiles

import android.arch.persistence.room.Insert
import java.text.DecimalFormat

interface AccessriesDAO {
    @Insert()
    fun insertAcce(lockID: Int, acces_ID: Int, prc: DecimalFormat, brd: String, qnty: Int, des)
}