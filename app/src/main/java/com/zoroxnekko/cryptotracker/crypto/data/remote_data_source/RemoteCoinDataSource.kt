package com.zoroxnekko.cryptotracker.crypto.data.remote_data_source

import android.os.Build
import androidx.annotation.RequiresApi
import com.zoroxnekko.cryptotracker.core.data.networking.constructUrl
import com.zoroxnekko.cryptotracker.core.data.networking.safeCall
import com.zoroxnekko.cryptotracker.core.domain.util.NetworkError
import com.zoroxnekko.cryptotracker.core.domain.util.Result
import com.zoroxnekko.cryptotracker.core.domain.util.map
import com.zoroxnekko.cryptotracker.crypto.data.dto.CoinHistoryDto
import com.zoroxnekko.cryptotracker.crypto.data.dto.CoinsResponseDto
import com.zoroxnekko.cryptotracker.crypto.data.mappers.toCoin
import com.zoroxnekko.cryptotracker.crypto.data.mappers.toCoinPrice
import com.zoroxnekko.cryptotracker.crypto.domain.Coin
import com.zoroxnekko.cryptotracker.crypto.domain.CoinDataSource
import com.zoroxnekko.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpClient.get(urlString = constructUrl("/assets/$coinId/history")) {
                parameter("interval", "h6")
                parameter("start", startMillis)
                parameter("end", endMillis)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}