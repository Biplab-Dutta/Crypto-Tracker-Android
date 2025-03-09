package com.zoroxnekko.cryptotracker.crypto.data

import com.zoroxnekko.cryptotracker.core.data.networking.safeCall
import com.zoroxnekko.cryptotracker.core.domain.util.NetworkError
import com.zoroxnekko.cryptotracker.core.domain.util.Result
import com.zoroxnekko.cryptotracker.crypto.domain.Coin
import com.zoroxnekko.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
       return safeCall {
          httpClient.get("/assets")
       }
    }

}