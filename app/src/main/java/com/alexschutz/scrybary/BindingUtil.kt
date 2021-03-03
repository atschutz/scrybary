package com.alexschutz.scrybary


import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

const val CMC_HEIGHT = 16

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
fun symbolText(view: TextView, text: String?) {
    view.replaceCMCs(text)
}

fun TextView.replaceCMCs(text: String?) {

    val str = SpannableString(text ?: "")
    var start = 0

    while (start + 1 < str.length) {

        if (str[start] != '{') start++
        else {
            var end = start + 1

            while (end <= str.length && str[end] != '}') end++
            if (end == str.length) return

            ResourcesCompat.getDrawable(
                resources,
                symbols[(str.slice(start..end)).toString()] ?: R.drawable.ic_dice_d20,
                null)?.let {
                    it.setBounds(
                        0,
                        0,
                        dpToPx(it.intrinsicWidth*CMC_HEIGHT/it.intrinsicHeight),
                        dpToPx(CMC_HEIGHT))

                    str.setSpan(
                        ImageSpan(it, ImageSpan.ALIGN_BOTTOM),
                        start,
                        end + 1,
                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            start = end + 1
        }
    }
    setText(str)
}

fun dpToPx(dp: Int) = (dp * Resources.getSystem().displayMetrics.density).toInt()