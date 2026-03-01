package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Suit
import org.jetbrains.compose.resources.painterResource
import playwithme.app.generated.resources.Res
import playwithme.app.generated.resources.back
import playwithme.app.generated.resources.clubs
import playwithme.app.generated.resources.diamonds
import playwithme.app.generated.resources.hearts
import playwithme.app.generated.resources.spades

@Composable
fun CardBack(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .size(Theme.Card.width, Theme.Card.height),
        shape = Theme.Card.shape,
        shadowElevation = Theme.Card.elevation,
        color = Theme.LightColorScheme.surfaceVariant,
        border = BorderStroke(0.5.dp, Color.LightGray),
    ) {
        Image(
            painter = painterResource(Res.drawable.back),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }
}
