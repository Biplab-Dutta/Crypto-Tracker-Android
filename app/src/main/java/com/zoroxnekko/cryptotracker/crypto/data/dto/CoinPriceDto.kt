package com.zoroxnekko.cryptotracker.crypto.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)
