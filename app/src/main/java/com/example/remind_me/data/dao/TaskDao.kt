package com.example.remind_me.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.remind_me.data.model.Task

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("Select * from taskTable order by id ASC")
    fun getAllTasks() : LiveData<List<Task>>

}