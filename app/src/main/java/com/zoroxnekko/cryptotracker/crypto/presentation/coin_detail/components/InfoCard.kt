package com.zoroxnekko.cryptotracker.crypto.presentation.coin_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zoroxnekko.cryptotracker.R
import com.zoroxnekko.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun InfoCard(
    title: String,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    formattedText: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    val defaultTextStyle = LocalTextStyle.current.copy(
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        color = contentColor,
    )
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 15.dp,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary,
            ),
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = contentColor,
        )
    ) {
        AnimatedContent(
            targetState = icon,
            modifier = modifier.align(Alignment.CenterHorizontally),
            label = "IconAnimation"
        ) {
            Icon(
                imageVector = it,
                contentDescription = title,
                modifier = Modifier
                    .padding(16.dp)
                    .size(75.dp),
                tint = contentColor,
            )
        }
        Spacer(modifier.height(8.dp))
        AnimatedContent(
            targetState = formattedText,
            modifier = modifier.align(Alignment.CenterHorizontally),
            label = "FormattedText"
        ) {
            Text(
                text = it,
                style = defaultTextStyle,
                modifier = modifier.padding(horizontal = 16.dp)
            )
        }
        Spacer(modifier.height(8.dp))
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            color = contentColor,
        )
    }
}

@PreviewLightDark
@Composable
private fun InfoCardPreview(modifier: Modifier = Modifier) {
    CryptoTrackerTheme {
        InfoCard(
            title = "Price",
            formattedText = "$ 76,322.87",
            icon = ImageVector.vectorResource(
                id = R.drawable.dollar
            )
        )
    }
}