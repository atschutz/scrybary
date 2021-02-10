package com.alexschutz.scrybary.view

import android.view.View
import androidx.fragment.app.Fragment

open class BackButtonFragment: Fragment(), BackButtonListener {
    override fun onBackPressed(v: View) {
        activity?.onBackPressed()
    }
}