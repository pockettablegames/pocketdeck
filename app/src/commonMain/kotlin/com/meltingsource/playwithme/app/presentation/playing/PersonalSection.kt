package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
import kotlin.math.min

@Composable
fun PersonalSection(
    playerName: String,
    hand: List<Card>,
    tricks: List<List<Card>>,
    onPlay: (String) -> Unit,
    onCollectTrick: () -> Unit,
    modifier: Modifier = Modifier,
    maxWidth: Dp
) {

    val cardsPerRow = remember( maxWidth) {
        ((maxWidth + Theme.Spacing.small - Theme.Spacing.medium * 7 - 64.dp) /
                (Theme.Card.width + Theme.Spacing.small)).toInt()
    }

    Column(
        modifier = modifier.padding(Theme.Spacing.medium)
    ) {
        Text(playerName)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(168.dp),
                tonalElevation = 1.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(cardsPerRow),
                    verticalArrangement = Arrangement.spacedBy(Theme.Spacing.small),
                    horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.small),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(
                        items = hand,
                        key = { it.id }
                    ) { card ->

                        PlayingCard(
                            card = card,
                            modifier = Modifier
                                .shadow(
                                    elevation = 1.dp,
                                    shape = Theme.Card.shape
                                )
                                .animateItem(),
                            onClick = { onPlay(card.id) }
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .padding(start = 8.dp),
                tonalElevation = 1.dp,
                shape = MaterialTheme.shapes.medium,
                onClick = onCollectTrick
            ) {
                Box(modifier = Modifier.padding(8.dp)) {
                    TrickStack(tricks.size)
                    Text("${tricks.size}")
                }
            }
        }
    }
}

@Composable
fun TrickStack(
    trickCount: Int,
    modifier: Modifier = Modifier
) {
    val visibleStacks = min(trickCount, 3)

    Box(modifier = modifier.size(Theme.Card.width, Theme.Card.height)) {

        repeat(visibleStacks) { index ->
            CardBack(
                modifier = Modifier
                    .size(Theme.Card.width, Theme.Card.height)
                    .offset(y = (-index * 3).dp)
                    .zIndex(index.toFloat())
            )
        }
    }
}