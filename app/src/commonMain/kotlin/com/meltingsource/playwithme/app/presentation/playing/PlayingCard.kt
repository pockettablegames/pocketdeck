package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
import com.meltingsource.playwithme.games.cards.Suit
import org.jetbrains.compose.resources.painterResource
import playwithme.app.generated.resources.Res
import playwithme.app.generated.resources.clubs
import playwithme.app.generated.resources.diamonds
import playwithme.app.generated.resources.hearts
import playwithme.app.generated.resources.spades

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
        color = Theme.Card.color,
        border = BorderStroke(0.5.dp, Color.LightGray),
    ) {
        Column {
            Column(
                modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (card.suit) {
                    Suit.DIAMONDS, Suit.HEARTS -> Text(card.rank, color = Color.Red)
                    else -> Text(card.rank, color = Color.Black)
                }
                Image(
                    painter = painterResource(
                        when (card.suit) {
                            Suit.DIAMONDS -> Res.drawable.diamonds
                            Suit.CLUBS -> Res.drawable.clubs
                            Suit.HEARTS -> Res.drawable.hearts
                            Suit.SPADES -> Res.drawable.spades
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}
