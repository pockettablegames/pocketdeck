package io.github.pockettablegames.pocketdeck.app.presentation.playing

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
import io.github.pockettablegames.pocketdeck.app.theme.Theme
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.DiscardType

@Composable
fun DiscardView(
    discardType: DiscardType,
    cards: List<Card>,
    modifier: Modifier = Modifier
) {
    if (discardType != DiscardType.HIDDEN) {
        val visibleCards = remember(cards) {
            if (cards.size <= 4) {
                cards
            } else {
                cards.subList(cards.size - 4, cards.size)
            }
        }

        Box(
            modifier = modifier.padding(12.dp)
        ) {
            visibleCards.forEachIndexed { index, card ->
                if (discardType == DiscardType.FACE_UP) {
                    PlayingCard(
                        card = card,
                        modifier = Modifier
                            .padding(
                                start = (index * 2).dp
                            )
                            .offset(0.dp,(-index * 2).dp)
                            .zIndex(index.toFloat())
                    )
                } else {
                    CardBack(
                        modifier = Modifier
                            .padding(
                                start = (index * 2).dp
                            )
                            .offset(0.dp,(-index * 2).dp)
                            .zIndex(index.toFloat())
                    )
                }

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
    } else {
        Box(Modifier)
    }
}