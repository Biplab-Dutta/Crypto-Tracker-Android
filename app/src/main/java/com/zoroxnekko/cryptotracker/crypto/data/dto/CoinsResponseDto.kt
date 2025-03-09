package com.zoroxnekko.cryptotracker.crypto.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinsResponseDto(
    val data: List<CoinDto>
)