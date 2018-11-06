package com.dblackwood.lockerroom.DBFiles

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import java.text.DecimalFormat

@Entity(tableName = "shoes", primaryKeys = ["shoe_id","locker_id"])
data class Shoes (
        @ColumnInfo(name = "price") var prc: DecimalFormat,
        @ColumnInfo(name = "brand") var brnd: String,
        @ColumnInfo(name = "quantity") var qty: Int,
        @ColumnInfo(name = "description") var descrip: String,
        @ColumnInfo(name = "color") var clr: String,
        @ColumnInfo(name = "size") var sz: String
)