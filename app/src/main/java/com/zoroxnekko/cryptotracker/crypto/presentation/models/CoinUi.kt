package com.zoroxnekko.cryptotracker.crypto.presentation.models

import android.icu.number.NumberFormatter
import android.icu.number.Precision
import androidx.annotation.DrawableRes
import com.zoroxnekko.cryptotracker.crypto.domain.Coin
import com.zoroxnekko.cryptotracker.core.presentation.util.getDrawableIdForCoin
import java.util.Locale

data class CoinUi(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @DrawableRes val iconRes: Int
)

data class DisplayableNumber(
    val value: Double,
    val formatted: String
)

fun Coin.toCoinUi(): CoinUi {
    return CoinUi(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd.toDisplayableNumber(),
        priceUsd = priceUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol)
    )
}

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormatter
        .withLocale(Locale.getDefault())
        .precision(Precision.fixedFraction(2))
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this).toString()
    )
}
