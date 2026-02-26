package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme

@Composable
fun <T> StackedCards(
    items: List<T>,
    modifier: Modifier = Modifier,
    cardContent: @Composable (T) -> Unit
) {
    BoxWithConstraints (
        modifier = modifier
    ) {
        val offset = remember(items) {
            min(Theme.Card.width / 2, (this.maxWidth - Theme.Card.width) / (items.size - 1).toFloat())
        }
        val initialOffset = remember(items, offset) {
            (this.maxWidth - (Theme.Card.width + offset * (items.size - 1))) / 2f
        }

        items.forEachIndexed { index, item ->
            Box(
                modifier = Modifier
                    .offset(x = initialOffset + offset * index)
                    .zIndex(index.toFloat())
                    .shadow(
                        elevation = Theme.Card.elevation + min(4.dp, index.dp),
                        shape = Theme.Card.shape
                    )
            ) {
                cardContent(item)
            }
        }
    }
}