package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.app.theme.Theme

@Composable
fun EmptyCard(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(Theme.Card.width, Theme.Card.height),
        shape = Theme.Card.shape,
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.Transparent
    ) {

    }
}
