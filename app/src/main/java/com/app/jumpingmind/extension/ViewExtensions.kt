package com.app.jumpingmind.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String?, @DrawableRes placeHolder: Int) {
    Glide.with(context)
        .load(url)
        .placeholder(placeHolder)
        .into(this)
}