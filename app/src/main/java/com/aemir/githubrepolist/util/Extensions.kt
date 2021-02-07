package com.aemir.githubrepolist.util

import android.content.Context
import android.view.View
import android.view.WindowInsets
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar

fun View.hideKeyboard() {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
        windowInsetsController?.hide(WindowInsets.Type.ime())
    } else {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}