package com.zoroxnekko.cryptotracker.core.navigation

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zoroxnekko.cryptotracker.core.presentation.util.EffectsObserver
import com.zoroxnekko.cryptotracker.core.presentation.util.toString
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListAction
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.components.CoinListScreen
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3AdaptiveApi
@Composable
fun AdaptiveCoinListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    EffectsObserver(events = viewModel.effects) { event ->
        when (event) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.toString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)
                        when (action) {
                            is CoinListAction.OnCoinTap -> {
                                navigator.navigateTo(
                                    pane = ListDetailPaneScaffoldRole.Detail
                                )
                            }
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        },
    )
}