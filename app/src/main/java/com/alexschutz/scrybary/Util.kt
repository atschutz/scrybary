package com.alexschutz.scrybary

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

fun ImageView.loadImageFromUri(uri: String?) {
    Glide.with(this).load(uri).into(this)
}

@BindingAdapter("android:imageUri")
fun loadImage(view: ImageView, uri: String?) {
    view.loadImageFromUri(uri)
}

// TODO reformat to use tint instead of different drawable for each case.
fun TextView.setBackgroundForLegality(text: String) {
    background = when (text) {
        "LEGAL" -> getDrawable(R.drawable.rect_legal)
        "RESTRICTED" -> getDrawable(R.drawable.rect_restricted)
        "BANNED" -> getDrawable(R.drawable.rect_banned)
        else -> getDrawable(R.drawable.rect_not_legal)
    }
}

// Utility function for function above.
fun TextView.getDrawable(id: Int) = ResourcesCompat.getDrawable(resources, id, null)

@BindingAdapter("android:legalityBackground")
fun setLegalityBackground(view: TextView, text: String) {
    view.setBackgroundForLegality(text)
}