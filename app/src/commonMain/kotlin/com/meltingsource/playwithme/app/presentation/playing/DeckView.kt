package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.min

@Composable
fun DeckView(
    count: Int,
    modifier: Modifier = Modifier
) {
    val visibleLayers = min(count, 4)

    Box(
        modifier = modifier.padding(8.dp)
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

        if(count == 0) {
            EmptyCard()
        }
    }
}

