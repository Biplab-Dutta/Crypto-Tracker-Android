package com.zoroxnekko.cryptotracker.crypto.data.remote_data_source

import com.zoroxnekko.cryptotracker.core.data.networking.constructUrl
import com.zoroxnekko.cryptotracker.core.data.networking.safeCall
import com.zoroxnekko.cryptotracker.core.domain.util.NetworkError
import com.zoroxnekko.cryptotracker.core.domain.util.Result
import com.zoroxnekko.cryptotracker.core.domain.util.map
import com.zoroxnekko.cryptotracker.crypto.data.dto.CoinsResponseDto
import com.zoroxnekko.cryptotracker.crypto.data.mappers.toCoin
import com.zoroxnekko.cryptotracker.crypto.domain.Coin
import com.zoroxnekko.cryptotracker.crypto.domain.CoinDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}