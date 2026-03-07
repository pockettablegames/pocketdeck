package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.github.pockettablegames.pocketdeck.app.theme.Theme
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.DeckType
import kotlin.math.min

@Composable
fun DeckView(
    deckType: DeckType,
    cards: List<Card>,
    modifier: Modifier = Modifier
) {
    if (deckType != DeckType.HIDDEN) {
        val visibleCount = remember(cards) {
            when(deckType) {
                DeckType.FACE_DOWN_LAST_UP -> {
                    cards.size.coerceAtMost(5)
                }
                else -> {
                    cards.size.coerceAtMost(4)
                }
            }
        }

        Box(
            modifier = modifier.padding(12.dp)
        ) {
            if (deckType == DeckType.FACE_DOWN_LAST_UP && cards.isNotEmpty()) {
                PlayingCard(
                    card = cards.last(),
                    modifier = Modifier
                        .zIndex(-1f)
                )
                repeat(visibleCount - 1) { index ->
                    CardBack(
                        modifier = Modifier
                            .padding(
                                start = Theme.Card.width / 3 + (index * 2).dp
                            )
                            .offset(0.dp,(-index * 2).dp)
                            .zIndex(index.toFloat())
                    )
                }
            } else {
                repeat(visibleCount) { index ->
                    CardBack(
                        modifier = Modifier
                            .padding(
                                start = (index * 2).dp
                            )
                            .offset(0.dp,(-index * 2).dp)
                            .zIndex(index.toFloat())
                    )
                }
            }

            if (visibleCount == 0) {
                EmptyCard()
            }
        }
    } else {
        Box(Modifier)
    }
}
