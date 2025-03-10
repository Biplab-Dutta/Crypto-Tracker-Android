package com.zoroxnekko.cryptotracker.crypto.presentation.coin_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zoroxnekko.cryptotracker.crypto.presentation.models.DisplayableNumber
import com.zoroxnekko.cryptotracker.ui.theme.CryptoTrackerTheme
import com.zoroxnekko.cryptotracker.ui.theme.greenBackground

@Composable
fun PriceChange(
    change: DisplayableNumber,
    modifier: Modifier = Modifier
) {
    val color = if (change.value > 0.0) {
        Color.Green
    } else {
        MaterialTheme.colorScheme.onErrorContainer
    }
    val backgroundColor = if (change.value > 0.0) {
        greenBackground
    } else {
        MaterialTheme.colorScheme.errorContainer
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100f))
            .background(backgroundColor)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (change.value > 0) {
                Icons.Default.KeyboardArrowUp
            } else {
                Icons.Default.KeyboardArrowDown
            },
            contentDescription = null,
            modifier = modifier.size(20.dp),
            tint = color,
        )
        Text(
            text = "${change.formatted} %",
            color = contentColor,
            fontSize = 14.sp
        )
    }
}

@Composable
@PreviewLightDark
private fun PriceChangePreview() {
    CryptoTrackerTheme {
        PriceChange(
            change = DisplayableNumber(
                value = 2.43,
                formatted = "2.43",
            ),
        )
    }
}
