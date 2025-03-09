package com.zoroxnekko.cryptotracker.crypto.domain

import com.zoroxnekko.cryptotracker.core.domain.util.NetworkError
import com.zoroxnekko.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}