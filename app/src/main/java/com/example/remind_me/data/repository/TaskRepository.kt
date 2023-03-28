package com.example.remind_me.data.repository

import androidx.lifecycle.LiveData
import com.example.remind_me.data.dao.TaskDao
import com.example.remind_me.data.model.Task

class TaskRepository(private val tasksDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = tasksDao.getAllTasks()

    suspend fun insert(task: Task){
        tasksDao.insert(task)
    }

    suspend fun delete(task: Task){
        tasksDao.delete(task)
    }


}