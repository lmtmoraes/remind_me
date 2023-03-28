package com.example.remind_me.utils

import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBarRed(message: String) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackBar.setBackgroundTint(Color.RED)
    snackBar.show()
}