package com.example.remind_me.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.hardware.input.InputManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CalendarView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.remind_me.R
import com.example.remind_me.databinding.ActivityMainBinding
import com.example.remind_me.utils.showSnackBarRed
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }


        onClick()

    }

    private fun onClick() {
        binding.createBtn.setOnClickListener {
            hideKeyboard(binding.createBtn)
            validate()
        }
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
                    Toast.makeText(applicationContext, "validação", Toast.LENGTH_LONG).show()
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
}