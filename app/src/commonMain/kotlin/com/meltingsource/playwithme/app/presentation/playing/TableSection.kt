package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.games.cards.Card
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TableSection(
    players: List<PlayerSummaryUi>,
    activePlayer: PlayerSummaryUi?,
    table: Map<String, List<Card>>,
    modifier: Modifier = Modifier,
    maxWidth: Dp
) {
    val slot = remember(players, maxWidth) {
        (maxWidth - Theme.Spacing.medium * (players.size + 3)) / players.size.toFloat()
    }

    Surface(
        modifier = modifier.padding(Theme.Spacing.medium),
        tonalElevation = Theme.Table.tonalElevation,
        shape = Theme.Table.shape
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Theme.Spacing.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.Spacing.large)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.medium)
            ) {
                players.forEach { player ->
                    val cards = remember(player, table) {
                        table[player.id] ?: emptyList()
                    }

                    if (cards.isNotEmpty()) {
                        StackedCards(
                            cards,
                            Modifier
                                .width(slot)
                                .height(Theme.Card.height)
                        ) {
                            PlayingCard(
                                card = it,
                            )
                        }
                    } else {
                        Box(
                            Modifier
                                .width(slot)
                                .height(Theme.Card.height)
                        )
                    }
                }
            }

            activePlayer?.let {
                table[activePlayer.id]?.let { cards ->
                    StackedCards(
                        cards,
                        Modifier
                            .width(slot)
                            .height(Theme.Card.height)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        PlayingCard(
                            card = it,
                        )
                    }
                }
            } ?: run {
                Box(
                    Modifier
                    .width(slot)
                    .height(Theme.Card.height)
                    .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}