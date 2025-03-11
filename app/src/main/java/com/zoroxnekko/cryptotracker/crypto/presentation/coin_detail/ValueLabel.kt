package com.zoroxnekko.cryptotracker.crypto.presentation.coin_detail

import android.annotation.SuppressLint
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import java.util.Locale

data class ValueLabel(
    val value: Float,
    val unit: String
) {
    @SuppressLint("NewApi")
    fun formatted(): String {
        val fractionDigits = when {
            value > 1000 -> 0
            value in 2f..999f -> 2
            else -> 3
        }

        val formatter = NumberFormatter.withLocale(Locale.getDefault())
            .precision(Precision.minMaxFraction(0, fractionDigits))

        return "${formatter.format(value)}$unit"
    }
}
