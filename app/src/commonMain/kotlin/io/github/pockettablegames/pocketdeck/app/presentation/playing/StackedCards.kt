package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import io.github.pockettablegames.pocketdeck.app.theme.Theme

@Composable
fun <T> StackedCards(
    items: List<T>,
    modifier: Modifier = Modifier,
    cardWidth: Dp = Theme.Card.width,
    expand: Boolean = false,
    first: Boolean = false,
    last: Boolean = false,
    maxCards: Int = Int.MAX_VALUE,
    cardContent: @Composable (T) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val cards = remember(items) {
            if(maxCards < items.size) {
                items.subList(0, maxCards)
            } else {
                items
            }
        }

        val offset = remember(cards) {
            val maxW =
                if (expand) {
                    max(this.maxWidth, cardWidth + cardWidth * 1.8f)
                } else {
                    this.maxWidth
                }

            min(cardWidth / 2, (maxW - cardWidth) / (cards.size - 1).toFloat())
        }

        val initialOffset = remember(cards, offset) {
            val stackWidth = cardWidth + offset * (cards.size - 1)
            val value = (this.maxWidth - stackWidth) / 2f
            if (first) {
                value.coerceAtLeast(0.dp)
            } else if (last) {
                value.coerceAtMost(this.maxWidth - stackWidth)
            } else {
                value
            }
        }

        Box {
            cards.forEachIndexed { index, item ->

                Box(
                    modifier = Modifier
                        .offset(x = initialOffset + offset * index)
                        .zIndex(index.toFloat())
                        .shadow(
                            elevation = Theme.Card.elevation,
                            shape = Theme.Card.shape
                        )
                ) {
                    cardContent(item)
                }
            }

            if(cards.size < items.size) {
                Badge(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(
                            x = initialOffset + offset * cards.lastIndex + (6).dp,
                            y = (-6).dp
                        )
                        .zIndex(cards.size.toFloat())
                        .size(24.dp)
                        .pointerInput(Unit) { }
                ) {
                    Text(items.size.toString())
                }
            }
        }
    }
}