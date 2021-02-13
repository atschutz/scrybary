package com.alexschutz.scrybary.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

open class BackButtonFragment: Fragment(), BackButtonListener {
    override fun onBackPressed(v: View) {
        activity?.onBackPressed()
        v.hideKeyboard()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}