package com.alexschutz.scrybary


import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
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

@BindingAdapter("android:legalityBackground")
fun setLegalityBackground(view: TextView, text: String) {
    view.setBackgroundForLegality(text)
}

fun TextView.getDrawable(id: Int) = ResourcesCompat.getDrawable(resources, id, null)

@BindingAdapter("android:symbolText")
fun symbolText(view: TextView, text: String) {
    view.replaceCMCs(text)
}

fun TextView.replaceCMCs(text: String) {
    val ss = SpannableString(text)
    if (text.contains("{B}")) {
        val startSpan = text.indexOf("{B}")
        val res = ResourcesCompat.getDrawable(resources, R.drawable.rect_banned, null)
        res?.setBounds(0, 0, dpToPx(20), dpToPx(20))
        if (res != null) {
            val imageSpan = ImageSpan(res, ImageSpan.ALIGN_BASELINE)
            ss.setSpan(imageSpan, startSpan, startSpan + 3, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            setText(text.replaceFirst("{B}", "  "))
        }
    }
    append(ss)
}

private fun dpToPx(dp: Int) = (dp * Resources.getSystem().displayMetrics.density).toInt()