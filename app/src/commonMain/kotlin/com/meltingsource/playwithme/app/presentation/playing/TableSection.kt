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
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
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
    onDraw: () -> Unit,
    onCollectDiscard: () -> Unit
) {
    val slot = remember(players, maxWidth) {
        (maxWidth - Theme.Spacing.medium * (players.size + 3)) / players.size.toFloat()
    }

    val stackWidth = remember(slot) {
        min(slot, maxWidth * 0.5f)
    }

    Surface(
        modifier = modifier.padding(Theme.Spacing.medium),
        color = Theme.LightColorScheme.primaryContainer,
        shape = Theme.Table.shape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Theme.Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.Spacing.xlarge)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.medium)
            ) {
                players.forEach { player ->
                    Box(
                        Modifier
                            .width(slot),
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
                                .height(Theme.Card.height)
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