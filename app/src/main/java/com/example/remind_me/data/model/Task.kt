package com.example.remind_me.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskTable")
class Task(
    @ColumnInfo(name = "date") val taskDate: String,
    @ColumnInfo(name = "description") val taskDescription: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}