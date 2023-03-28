package com.example.remind_me.data.data_base

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remind_me.data.dao.TaskDao
import com.example.remind_me.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase(){

    abstract fun getTasksDao() : TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase?= null

        fun getDatabase(context: Context) : TaskDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                instance
            }


        }

    }

}