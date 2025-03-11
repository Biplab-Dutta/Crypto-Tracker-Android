package com.zoroxnekko.cryptotracker

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zoroxnekko.cryptotracker.core.presentation.util.EffectsObserver
import com.zoroxnekko.cryptotracker.core.presentation.util.toString
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_detail.CoinDetailScreen
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListEvent
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.components.CoinListScreen
import com.zoroxnekko.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                KoinAndroidContext {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val viewModel = koinViewModel<CoinListViewModel>()
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
                        when {
                            state.selectedCoin != null -> {
                                CoinDetailScreen(
                                    state = state,
                                    modifier = Modifier.padding(innerPadding),
                                )
                            }

                            else -> {
                                CoinListScreen(
                                    state = state,
                                    onAction = { action -> viewModel.onAction(action) },
                                    modifier = Modifier.padding(innerPadding),
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}
