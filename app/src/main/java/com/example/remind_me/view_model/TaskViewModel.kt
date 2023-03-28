package com.example.remind_me.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.remind_me.data.data_base.TaskDatabase
import com.example.remind_me.data.model.Task
import com.example.remind_me.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    val allTasks: LiveData<MutableList<Task>>
    val repository: TaskRepository

    init {
        val dao = TaskDatabase.getDatabase(application).getTasksDao()
        repository = TaskRepository(dao)
        allTasks = repository.allTasks
    }


    fun deleteTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }

    fun addTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }


}