package com.alexschutz.scrybary

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(windowToken, 0)
}

fun TextView.toggleVisibility(text: String?) {
    visibility = if (text.isNullOrEmpty()) View.GONE else View.VISIBLE
}