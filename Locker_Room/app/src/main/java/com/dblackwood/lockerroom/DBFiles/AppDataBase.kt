package com.dblackwood.lockerroom.dbfiles

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [Accessories::class, Dresses::class, Locker::class, Other::class, OuterWear::class, Pants::class, Shoes::class, Socks::class, Tees::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun accessoriesDAO(): AccessoriesDAO
    abstract fun dressesDAO(): DressesDAO
    abstract fun lockerDAO(): LockerDAO
    abstract fun otherDAO(): OtherDAO
    abstract fun outerWearDAO(): OuterWearDAO
    abstract fun pantsDAO(): PantsDAO
    abstract fun shoesDAO(): ShoesDAO
    abstract fun socksDAO(): SocksDAO
    abstract fun teesDAO(): TeesDAO

    companion object {
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase?
        {
            if (INSTANCE == null)
            {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDataBase::class.java, "Clothing.db").allowMainThreadQueries()
                            .build()
                }
            }

            return INSTANCE
        }

        fun destroyInstance()
        {
            INSTANCE = null
        }
    }
}