package com.example.remind_me.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remind_me.R
import com.example.remind_me.data.model.Task
import com.example.remind_me.databinding.ActivityMainBinding
import com.example.remind_me.utils.MaskEditUtil
import com.example.remind_me.utils.showSnackBarRed
import com.example.remind_me.view_model.TaskViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), TaskClickInterface, TaskDeleteInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private var taskAdapter = TaskAdapter(this, this, this)

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }

        onClick()
        maskViews()
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(TaskViewModel::class.java)
        viewModel.allTasks.observe(this, androidx.lifecycle.Observer { list ->
            list?.let {
                initList(it)
            }
        })


    }


    private fun onClick() {
        binding.createBtn.setOnClickListener {
            hideKeyboard(binding.createBtn)
            validate()
        }
    }

    private fun maskViews(){
        binding.editDate.addTextChangedListener(MaskEditUtil.mask(binding.editDate, "##/##/####"))
    }


    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }


    @SuppressLint("SimpleDateFormat")
    private fun validate() {
        val name = binding.editName.text.toString()
        val date = binding.editDate.text.toString()
        val calendar = Calendar.getInstance()
        val formattedData = SimpleDateFormat("dd/MM/yyyy")





        if(name.isEmpty() && date.isEmpty()){
            binding.root.showSnackBarRed(binding.root.context.getString(R.string.preencha_os_campos))

        }

        if (name.isEmpty() && date.isNotEmpty()) {
            binding.root.showSnackBarRed(binding.root.context.getString(R.string.campo_nome_obrigatorio))

        }
        if(name.isNotEmpty() && date.isEmpty()){
            binding.root.showSnackBarRed(binding.root.context.getString(R.string.campo_data_obrigatorio))
        }

        if(name.isNotEmpty() && date.isNotEmpty() && date.length < 10){
            binding.root.showSnackBarRed(binding.root.context.getString(R.string.digite_uma_data_valida))
        }

        if(name.isNotEmpty() && date.isNotEmpty() && date.length == 10){
            if(validateDate(date)){
                if(calendar.time.before(formattedData.parse(date))){
                    viewModel.addTask(Task(date, name))
                } else {
                    binding.root.showSnackBarRed(binding.root.context.getString(R.string.digite_uma_data_futura))
                }
            } else {
                binding.root.showSnackBarRed(binding.root.context.getString(R.string.digite_uma_data_valida))
            }
        }



    }

    @SuppressLint("SimpleDateFormat")
    fun validateDate(date: String) : Boolean{
        val formattedData = SimpleDateFormat("dd/MM/yyyy")
        formattedData.isLenient = false
        return try {
            formattedData.parse(date)
            true
        } catch (e: Exception){
            false
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun initList(list: MutableList<Task>) {
        val sortedList = list.sortedBy { it ->
            SimpleDateFormat("dd/MM/yyyy").parse(it.taskDate)
        }

        binding.rvTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = taskAdapter
            taskAdapter.updateList(sortedList)
        }
    }


    override fun onDeleteIconClick(task: Task) {
        viewModel.deleteTask(task)
    }

    override fun onTaskClick(task: Task) {
        viewModel.addTask(task)
    }
}