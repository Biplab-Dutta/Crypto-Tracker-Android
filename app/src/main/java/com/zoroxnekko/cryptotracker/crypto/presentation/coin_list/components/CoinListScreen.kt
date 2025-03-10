package com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.zoroxnekko.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListScreen(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.coins) { coinUi ->
                CoinListItem(
                    coinUi = coinUi,
                    onClick = { onAction(CoinListAction.OnCoinTap(coinUi)) },
                )
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun CoinListScreenPreview(modifier: Modifier = Modifier) {
    CryptoTrackerTheme {
        CoinListScreen(
            state = CoinListState(
                coins = (1..10).map {
                    previewCoinUi.copy(id = it.toString())
                }
            ),
            onAction = {}
        )
    }
}