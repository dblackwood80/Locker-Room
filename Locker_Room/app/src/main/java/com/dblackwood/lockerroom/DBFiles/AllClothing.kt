package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

open class AllClothing (

    @PrimaryKey(autoGenerate = true) var acce_id: Long,
    @ColumnInfo(name = "locker_id") var lck_id: Int,
    @ColumnInfo(name = "price") var prc: Double,
    @ColumnInfo(name = "brand") var brnd: String,
    @ColumnInfo(name = "quantity") var qty: Int,
    @ColumnInfo(name = "description") var descrip: String?,
    @ColumnInfo(name = "color") var clr: String,
    @ColumnInfo(name = "size") var sz: String
)

{
    constructor():this(0,0,0.0,"",0,"","","")
}