package com.zoroxnekko.cryptotracker.crypto.domain

import com.zoroxnekko.cryptotracker.core.domain.util.NetworkError
import com.zoroxnekko.cryptotracker.core.domain.util.Result
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>, NetworkError>
}