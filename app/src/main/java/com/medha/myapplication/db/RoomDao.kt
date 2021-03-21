package com.medha.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.medha.myapplication.db.tables.CourierTable

@Dao
interface RoomDao {

    /**
     * An intermediate class between user and sqlite database .
     * All the operations like insert, delete and update are defined here
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourierList(courierList: ArrayList<CourierTable>)

    @Query("SELECT * FROM ${CourierTable.TABLE_NAME} WHERE 1")
    fun getAllCourierLogo(): List<CourierTable>

}