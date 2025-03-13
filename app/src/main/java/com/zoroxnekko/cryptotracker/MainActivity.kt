package com.zoroxnekko.cryptotracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.ui.Modifier
import com.zoroxnekko.cryptotracker.core.navigation.AdaptiveCoinListDetailPane
import com.zoroxnekko.cryptotracker.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.KoinAndroidContext

@ExperimentalMaterial3AdaptiveApi
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {
                KoinAndroidContext {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AdaptiveCoinListDetailPane(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}
