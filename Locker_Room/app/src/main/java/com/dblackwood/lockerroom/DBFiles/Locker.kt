package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "locker")
data class Locker (
        @PrimaryKey(autoGenerate = true) var lock_id: Long?,
        @ColumnInfo(name = "name") var name: String
){
    constructor():this(0,"")
}