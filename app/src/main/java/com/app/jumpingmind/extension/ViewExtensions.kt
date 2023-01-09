package com.app.jumpingmind.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String, @DrawableRes placeHolder: Int? = null) {
    Glide.with(context)
        .load(url)
        .into(this)
}