package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme

@Composable
fun <T> StackedCards(
    items: List<T>,
    modifier: Modifier = Modifier,
    cardWidth: Dp = Theme.Card.width,
    expand: Boolean = false,
    first: Boolean = false,
    last: Boolean = false,
    cardContent: @Composable (T) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val offset = remember(items) {
            val maxW =
                if (expand) {
                    max(this.maxWidth, cardWidth + cardWidth * 1.8f)
                } else {
                    this.maxWidth
                }

            min(cardWidth / 2, (maxW - cardWidth) / (items.size - 1).toFloat())
        }

        val initialOffset = remember(items, offset) {
            val stackWidth = cardWidth + offset * (items.size - 1)
            val value = (this.maxWidth - stackWidth) / 2f
            if (first) {
                value.coerceAtLeast(0.dp)
            } else if (last) {
                value.coerceAtMost(this.maxWidth - stackWidth)
            } else {
                value
            }
        }

        items.forEachIndexed { index, item ->

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
    }
}