package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
import com.meltingsource.playwithme.games.cards.CardsConfig
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TableSection(
    players: List<PlayerSummaryUi>,
    activePlayer: PlayerSummaryUi?,
    table: Map<String, List<Card>>,
    discard: List<Card>,
    modifier: Modifier = Modifier,
    maxWidth: Dp,
    deckCount: Int,
    config: CardsConfig,
    onDraw: () -> Unit,
    onCollectDiscard: () -> Unit
) {
    val othersCount = remember(players) {
        players.size - 1
    }

    val slot = remember(players, maxWidth) {
        (maxWidth - (Theme.Spacing.medium * 2 + Theme.Spacing.medium * (othersCount - 1))) / othersCount.toFloat()
    }

    val stackWidth = remember(slot) {
        min(slot, maxWidth * 0.5f)
    }

    Surface(
        modifier = modifier,
        color = Theme.LightColorScheme.primaryContainer,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = Theme.Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.Spacing.xlarge)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.Spacing.medium),
                horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.medium)
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
                            .width(slot)
                            .zIndex(if(index % 2 == 1) {
                                index + othersCount.toFloat()
                            } else {
                                index.toFloat()
                            }),
                        contentAlignment = Alignment.Center
                    ) {

                        val cards = remember(player, table) {
                            table[player.id] ?: emptyList()
                        }

                        if (cards.isNotEmpty()) {
                            StackedCards(
                                cards,
                                Modifier
                                    .width(stackWidth)
                                    .height(Theme.Card.height)
                                    .offset(0.dp, if(index % 2 == 1) {
                                        Theme.Card.height * 0.6f
                                    } else {
                                        0.dp
                                    }),
                                expand = true,
                                first = index == 0,
                                last = index == (othersCount - 1)
                            ) {
                                PlayingCard(
                                    card = it,
                                )
                            }
                        } else {
                            Box(
                                Modifier
                                    .width(stackWidth)
                                    .height(Theme.Card.height)
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {

                DeckView(
                    deckCount,
                    Modifier
                        .clickable(
                            onClick = onDraw
                        )
                )

                activePlayer?.let {
                    table[activePlayer.id]?.let { cards ->
                        StackedCards(
                            cards,
                            Modifier
                                .width(stackWidth)
                                .height(Theme.Card.height),
                            expand = true
                        ) {
                            PlayingCard(
                                card = it,
                            )
                        }
                    }
                } ?: run {
                    Box(
                        Modifier
                            .width(stackWidth)
                            .height(Theme.Card.height)
                    )
                }

                DiscardView(
                    discard,
                    Modifier
                        .clickable(
                            onClick = onCollectDiscard
                        )
                )
            }
        }
    }
}