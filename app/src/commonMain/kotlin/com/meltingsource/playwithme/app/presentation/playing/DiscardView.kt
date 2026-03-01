package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.games.cards.Card

@Composable
fun DiscardView(
    cards: List<Card>,
    modifier: Modifier = Modifier
) {
    val visibleCards = remember(cards) {
        if (cards.size <= 4) {
            cards
        } else {
            cards.subList(cards.size - 4, cards.lastIndex)
        }
    }

    Box(
        modifier = modifier.padding(12.dp)
    ) {
        visibleCards.forEachIndexed { index, card ->
            PlayingCard(
                card = card,
                modifier = Modifier
                    .offset(
                        x = (index * 2).dp,
                        y = (-index * 2).dp
                    )
                    .zIndex(index.toFloat())
            )

            if (index == visibleCards.lastIndex) {
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(
                            x = (6 + index * 2).dp,
                            y = (-6 - index * 2).dp
                        )
                        .zIndex(index + 1f)
                        .size(24.dp)
                        .pointerInput(Unit) { }
                ) {
                    Text(cards.size.toString())
                }
            }
        }

        if (cards.isEmpty()) {
            EmptyCard()
        }
    }
}

