package com.zoroxnekko.cryptotracker.crypto.presentation.coin_list

import com.zoroxnekko.cryptotracker.core.domain.util.NetworkError

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}