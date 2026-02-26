package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
import kotlin.math.min

@Composable
fun DeckDiscardRow(
    deckCount: Int,
    discard: List<Card>,
    onDraw: () -> Unit,
    onCollectDiscard: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Surface(
            modifier = Modifier.size(64.dp, 80.dp),
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.medium,
            onClick = onDraw
        ) {
                DeckView(
                    deckCount,
                    Modifier
                        .padding(8.dp)
                        .padding(top = 8.dp)
                )
        }

        Surface(
            modifier = Modifier.size(64.dp, 80.dp),
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.medium,
            onClick = onCollectDiscard
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Text("Discard (${discard.size})", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun DeckView(
    count: Int,
    modifier: Modifier = Modifier
) {
    val visibleLayers = min(count, 4)

    Box(
        modifier = modifier
            .size(72.dp, 104.dp)
    ) {

        repeat(visibleLayers) { index ->
            CardBack(
                modifier = Modifier
                    .offset(
                        x = (index * 2).dp,
                        y = (-index * 2).dp
                    )
                    .zIndex(index.toFloat())
            )
        }
    }
}

@Composable
fun CardBack(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(Theme.Card.width, Theme.Card.height),
        shape = Theme.Card.shape,
        shadowElevation = Theme.Card.elevation,
        color = Color.White,
    ) {

    }
}
