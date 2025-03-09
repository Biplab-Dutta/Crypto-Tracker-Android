package com.zoroxnekko.cryptotracker.crypto.presentation.coin_list

import com.zoroxnekko.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi) : CoinListAction
}