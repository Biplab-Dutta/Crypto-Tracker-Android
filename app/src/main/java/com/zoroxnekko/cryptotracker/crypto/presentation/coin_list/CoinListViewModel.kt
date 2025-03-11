package com.zoroxnekko.cryptotracker.crypto.presentation.coin_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zoroxnekko.cryptotracker.core.domain.util.onError
import com.zoroxnekko.cryptotracker.core.domain.util.onSuccess
import com.zoroxnekko.cryptotracker.crypto.domain.CoinDataSource
import com.zoroxnekko.cryptotracker.crypto.presentation.models.CoinUi
import com.zoroxnekko.cryptotracker.crypto.presentation.models.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state.onStart { loadCoins() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _effects = Channel<CoinListEvent>()
    val effects = _effects.receiveAsFlow()

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            coinDataSource.getCoins()
                .onSuccess { coins ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            coins = coins.map { it.toCoinUi() }
                        )
                    }
                }
                .onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _effects.send(CoinListEvent.Error(error))
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectCoin(coinUI: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUI) }
        viewModelScope.launch {
            coinDataSource.getCoinHistory(
                coinId = coinUI.id,
                start = ZonedDateTime.now().minusDays(5),
                end = ZonedDateTime.now(),
            )
                .onSuccess { history -> println(history) }

                .onError { error ->
                    _effects.send(CoinListEvent.Error(error))
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinTap -> {
                selectCoin(action.coinUi)
            }
        }
    }
}