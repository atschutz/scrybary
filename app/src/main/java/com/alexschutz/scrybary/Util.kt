package com.alexschutz.scrybary

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

fun ImageView.loadImageFromUri(uri: String?) {
    Glide.with(this).load(uri).into(this)
}

@BindingAdapter("android:imageUri")
fun loadImage(view: ImageView, uri: String?) {
    view.loadImageFromUri(uri)
}