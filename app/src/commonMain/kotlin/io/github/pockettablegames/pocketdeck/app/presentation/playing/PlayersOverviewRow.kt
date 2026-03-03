package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import io.github.pockettablegames.pocketdeck.app.theme.Theme
import io.github.pockettablegames.pocketdeck.app.theme.rememberAvatars
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import org.jetbrains.compose.resources.painterResource
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun PlayersOverviewRow(
    players: List<PlayerSummaryUi>,
    activePlayer: PlayerSummaryUi?,
    maxWidth: Dp,
    config: CardsConfig
) {
    val avatars = rememberAvatars()

    val othersCount = remember(players) {
        players.size - 1
    }

    val slot = remember(players, maxWidth) {
        (maxWidth - (Theme.Spacing.medium * 2 + Theme.Spacing.medium * (othersCount - 1))) / othersCount.toFloat()
    }

    val stackWidth = remember(slot) {
        min(slot, maxWidth * 0.5f)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.Spacing.medium)
            .padding(top = Theme.Spacing.medium, bottom = Theme.Spacing.large),
        horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.medium),
    ) {
        val startIndex = remember(players, activePlayer) {
            (players.indexOf(activePlayer).let {
                if (it < 0) {
                    0
                } else {
                    it
                }
            } + if (config.playersOrderClockwise) {
                1
            } else {
                -1
            }) % players.size
        }

        for (index in 0..<othersCount) {
            val player = players[
                (startIndex + if (config.playersOrderClockwise) {
                    index
                } else {
                    -index
                } + players.size
                        ) % players.size
            ]

            Box(
                Modifier
                    .width(slot),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .shadow(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(avatars[player.avatar]),
                                contentDescription = null,
                                modifier = Modifier.size(52.dp)
                            )
                        }
                        if (player.trickCount > 0) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(
                                        x = (6).dp,
                                        y = (-6).dp
                                    )
                                    .size(24.dp)
                                    .pointerInput(Unit) { }
                            ) {
                                Text(player.trickCount.toString())
                            }
                        }
                    }
                    Spacer(Modifier.height(Theme.Spacing.small))
                    Text(
                        player.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Theme.AppTypography.labelSmall
                    )
                    Spacer(Modifier.height(Theme.Spacing.small))
                    StackedCards(
                        List(player.handCount) { it },
                        Modifier
                            .width(stackWidth)
                            .height(Theme.Card.height * 0.7f),
                        cardWidth = Theme.Card.width * 0.7f,
                        maxCards = 4
                    ) {
                        CardBack(
                            Modifier.size(
                                Theme.Card.width * 0.7f,
                                Theme.Card.height * 0.7f
                            )
                        )
                    }
                }
            }
        }
    }
}