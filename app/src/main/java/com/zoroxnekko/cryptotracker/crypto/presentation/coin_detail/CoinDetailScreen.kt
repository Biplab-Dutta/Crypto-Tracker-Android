package com.zoroxnekko.cryptotracker.crypto.presentation.coin_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zoroxnekko.cryptotracker.R
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_detail.components.InfoCard
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.components.previewCoinUi
import com.zoroxnekko.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.zoroxnekko.cryptotracker.ui.theme.CryptoTrackerTheme
import com.zoroxnekko.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin
        val absoluteChangeFormatted =
            (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber()
        val isPositive = coin.changePercent24Hr.value > 0.0

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                color = contentColor,
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = contentColor,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                InfoCard(
                    title = stringResource(id = R.string.market_cap),
                    formattedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.stock),
                )
                InfoCard(
                    title = stringResource(id = R.string.price),
                    formattedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(R.drawable.dollar),
                )
                if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }
                InfoCard(
                    title = stringResource(id = R.string.change_last_24h),
                    formattedText = absoluteChangeFormatted.formatted,
                    icon = ImageVector.vectorResource(
                        if (isPositive) R.drawable.trending
                        else R.drawable.trending_down
                    ),
                )
            }
            AnimatedVisibility(visible = coin.priceHistory.isNotEmpty()) {
                var selectedDataPoint by remember {
                    mutableStateOf<DataPoint?>(null)
                }
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f),
                    dataPoints = coin.priceHistory,
                    style = ChartStyle(
                        chartLineColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.secondary,
                        selectedColor = MaterialTheme.colorScheme.primary,
                        helperLinesThicknessPx = 5f,
                        axisLinesThicknessPx = 5f,
                        labelFontSize = 14.sp,
                        minYLabelSpacing = 25.dp,
                        verticalPadding = 8.dp,
                        horizontalPadding = 8.dp,
                        xAxisLabelSpacing = 8.dp,
                    ),
                    visibleDataPointsIndices = coin.priceHistory.indices,
                    unit = "$",
                    selectedDataPoint = selectedDataPoint,
                    onDataPointSelected = {
                        selectedDataPoint = it
                    },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CoinDetailScreenPreview(modifier: Modifier = Modifier) {
    CryptoTrackerTheme {
        CoinDetailScreen(
            state = CoinListState(selectedCoin = previewCoinUi),
            modifier = modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}