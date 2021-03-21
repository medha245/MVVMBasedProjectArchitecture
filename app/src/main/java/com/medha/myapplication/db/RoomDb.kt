package com.medha.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.medha.myapplication.db.tables.CourierTable

@Database(entities = [CourierTable::class], version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {
    abstract fun dao(): RoomDao
}