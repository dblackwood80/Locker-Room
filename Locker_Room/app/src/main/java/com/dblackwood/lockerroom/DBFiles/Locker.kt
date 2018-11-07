package com.dblackwood.lockerroom.DBFiles

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "locker")
data class Locker (
        @PrimaryKey(autoGenerate = true) var lock_id: Int?,
        @ColumnInfo(name = "name") var name: String
){
}