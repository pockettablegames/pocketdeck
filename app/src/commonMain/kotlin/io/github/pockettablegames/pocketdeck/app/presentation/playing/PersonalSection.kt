package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.app.presentation.playerswitch.ActionUi
import io.github.pockettablegames.pocketdeck.app.theme.Theme
import io.github.pockettablegames.pocketdeck.app.theme.rememberAvatars
import io.github.pockettablegames.pocketdeck.games.cards.ActionType
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import org.jetbrains.compose.resources.painterResource

@Composable
fun PersonalSection(
    config: CardsConfig,
    player: PlayerSummaryUi,
    hand: List<Card>,
    tricks: List<List<Card>>,
    lastCollectedAction: ActionUi?,
    onPlay: (String) -> Unit,
    onCollectTrick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val avatars = rememberAvatars()

    val showDialog = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = Theme.Spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .shadow(2.dp)
                            .then(
                                if (lastCollectedAction?.playerId == player.id) {
                                    Modifier.border(
                                        width = 4.dp,
                                        color = Theme.Colors.glow,
                                        shape = CircleShape
                                    ).then(
                                        if ((lastCollectedAction.type == ActionType.CollectToTrick ||
                                                    lastCollectedAction.type == ActionType.CollectToDiscard)
                                        ) {
                                            Modifier.clickable(
                                                onClick = { showDialog.value = true }

                                            )
                                        } else {
                                            Modifier
                                        }
                                    )
                                } else {
                                    Modifier
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(avatars[player.avatar]),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                Spacer(Modifier.height(Theme.Spacing.small))
                Text(player.name)
            }

            if (config.showPlayerTrickZone) {
                TricksView(
                    tricks.size,
                    Modifier
                        .clickable(
                            onClick = onCollectTrick
                        )
                )
            } else {
                Box(Modifier)
            }
        }

        Spacer(Modifier.height(Theme.Spacing.small))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(Theme.Card.height + 16.dp * 2),
            color = Theme.LightColorScheme.primaryContainer
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.small),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                item {
                    Spacer(Modifier.width(16.dp - Theme.Spacing.small))
                }
                items(
                    items = hand,
                    key = { it.id }
                ) { card ->
                    PlayingCard(
                        card = card,
                        modifier = Modifier
                            .shadow(
                                elevation = 1.dp,
                                shape = Theme.Card.shape
                            )
                            .animateItem(),
                        onClick = { onPlay(card.id) }
                    )
                }
                item {
                    Spacer(Modifier.width(16.dp))
                }
            }
        }
    }

    lastCollectedAction?.let {
        ActionDialog(lastCollectedAction, showDialog)
    }
}
