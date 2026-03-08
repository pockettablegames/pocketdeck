package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.app.theme.Theme
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.Suit
import org.jetbrains.compose.resources.painterResource
import pocketdeck.app.generated.resources.Res
import pocketdeck.app.generated.resources.clubs
import pocketdeck.app.generated.resources.diamonds
import pocketdeck.app.generated.resources.hearts
import pocketdeck.app.generated.resources.spades

@Composable
fun PlayingCard(
    card: Card,
    requiredWidth: Dp = Theme.Card.width,
    requiredHeight: Dp = Theme.Card.height,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .requiredWidth(requiredWidth)
            .requiredHeight(requiredHeight)
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
