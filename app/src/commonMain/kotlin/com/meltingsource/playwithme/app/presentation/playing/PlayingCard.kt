package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
import org.jetbrains.compose.resources.painterResource
import playwithme.app.generated.resources.Res
import playwithme.app.generated.resources.hearts_king

@Composable
fun PlayingCard(
    card: Card,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .requiredWidth(Theme.Card.width)
            .requiredHeight(Theme.Card.height)
            .then(
                if (onClick != null)
                    Modifier.clickable { onClick() }
                else Modifier
            ),
        shadowElevation = Theme.Card.elevation,
        shape = Theme.Card.shape,
        color = Theme.Card.color
    ) {
        Box(
            modifier = Modifier.padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(Res.drawable.hearts_king),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}