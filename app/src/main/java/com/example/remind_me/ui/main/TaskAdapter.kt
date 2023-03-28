package com.example.remind_me.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remind_me.R
import com.example.remind_me.data.model.Task
import com.example.remind_me.databinding.ItemTaskBinding

class TaskAdapter(
    val context: Context,
    val taskClickInterface: TaskClickInterface,
    val taskDeleteInterface: TaskDeleteInterface

) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private lateinit var binding: ItemTaskBinding
    private val allTasks = ArrayList<Task>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date = binding.dateTxt
        val task = binding.taskTxt
        val delete = binding.deleteImg
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = allTasks[position].taskDate
        holder.task.text = allTasks[position].taskDescription
        holder.delete.setOnClickListener {
            taskDeleteInterface.onDeleteIconClick(allTasks[position])
        }
        holder.itemView.setOnClickListener {
            taskClickInterface.onTaskClick(allTasks[position])
        }
    }

    override fun getItemCount(): Int {
        return allTasks.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList : List<Task> ){
        allTasks.clear()
        allTasks.addAll(newList)
        notifyDataSetChanged()
    }

}

interface TaskDeleteInterface{
    fun onDeleteIconClick(task: Task)
}

interface TaskClickInterface {
    fun onTaskClick(task: Task)
}