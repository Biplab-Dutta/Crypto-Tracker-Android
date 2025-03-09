package com.zoroxnekko.cryptotracker.crypto.presentation.coin_list

import com.zoroxnekko.cryptotracker.crypto.presentation.models.CoinUi

sealed interface CoinListAction {
    data class OnCoinTap(val coinUi: CoinUi) : CoinListAction
}