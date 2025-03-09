package com.zoroxnekko.cryptotracker.crypto.data.mappers

import com.zoroxnekko.cryptotracker.crypto.data.dto.CoinDto
import com.zoroxnekko.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}