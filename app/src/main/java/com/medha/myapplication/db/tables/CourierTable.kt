package com.medha.myapplication.db.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = CourierTable.TABLE_NAME)
data class CourierTable(@PrimaryKey @SerializedName("base_courier_id") var baseCourierId: Long,
                        @ColumnInfo @SerializedName("name") var name: String,
                        @ColumnInfo @SerializedName("image") var image: String) {
    companion object {
        const val TABLE_NAME = "courier_table"
    }
}