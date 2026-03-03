package io.github.pockettablegames.pocketdeck.app.presentation.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import io.github.pockettablegames.pocketdeck.games.cards.Suit

@Composable
fun CardsConfigScreen(
    defaultConfig: CardsConfig,
    playersCount: Int,
    onStart: (CardsConfig) -> Unit
) {
    var cardsPerPlayer by remember { mutableStateOf(defaultConfig.cardsPerPlayer) }
    var clockwise by remember { mutableStateOf(defaultConfig.playersOrderClockwise) }
    var autoDeal by remember { mutableStateOf(defaultConfig.autoDeal) }
    var showDeck by remember { mutableStateOf(defaultConfig.showDeckZone) }
    var showDiscard by remember { mutableStateOf(defaultConfig.showDiscardZone) }
    var groupByRank by remember { mutableStateOf(defaultConfig.groupByRank) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text("Game Setup", style = MaterialTheme.typography.headlineMedium)

        // Cards per player
        Column {
            Text("Cards per player")
            Slider(
                value = cardsPerPlayer.toFloat(),
                onValueChange = { cardsPerPlayer = it.toInt() },
                valueRange = 1f..26f
            )
            Text("$cardsPerPlayer cards")
        }

        // Order
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Clockwise order")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = clockwise,
                onCheckedChange = { clockwise = it }
            )
        }

        // Auto deal
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Auto deal")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = autoDeal,
                onCheckedChange = { autoDeal = it }
            )
        }

        // Show deck
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Show deck")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = showDeck,
                onCheckedChange = { showDeck = it }
            )
        }

        // Show discard
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Show discard")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = showDiscard,
                onCheckedChange = { showDiscard = it }
            )
        }

        Spacer(Modifier.weight(1f))

        // Group By
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Group By Rank")
            Spacer(Modifier.weight(1f))
            Switch(
                checked = groupByRank,
                onCheckedChange = { groupByRank = it }
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                onStart(
                    defaultCardsConfig(
                        cardsPerPlayer = cardsPerPlayer,
                        clockwise = clockwise,
                        autoDeal = autoDeal,
                        showDeck = showDeck,
                        showDiscard = showDiscard,
                        groupByRank = groupByRank
                    )
                )
            },
            enabled = playersCount >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Game")
        }
    }
}

fun defaultCardsConfig(
    cardsPerPlayer: Int,
    clockwise: Boolean,
    autoDeal: Boolean,
    showDeck: Boolean,
    showDiscard: Boolean,
    groupByRank: Boolean
): CardsConfig {
    return CardsConfig(
        name = "Default",
        deck = Suit.entries.flatMap { suit ->
            (2..10).map {
                Card(
                    id = "${suit}_$it",
                    rank = it.toString(),
                    suit = suit,
                    rankOrder = it,
                    suitOrder = suit.ordinal
                )
            } + Card(
                id = "${suit}_A",
                rank = "A",
                suit = suit,
                rankOrder = 14,
                suitOrder = suit.ordinal
            ) + Card(
                id = "${suit}_K",
                rank = "K",
                suit = suit,
                rankOrder = 13,
                suitOrder = suit.ordinal
            ) + Card(
                id = "${suit}_Q",
                rank = "Q",
                suit = suit,
                rankOrder = 12,
                suitOrder = suit.ordinal
            ) + Card(
                id = "${suit}_J",
                rank = "J",
                suit = suit,
                rankOrder = 11,
                suitOrder = suit.ordinal
            )
        }.shuffled(),
        cardsPerPlayer = cardsPerPlayer,
        showDeckZone = showDeck,
        showDiscardZone = showDiscard,
        showPlayerTrickZone = true,
        refillDeckWithDiscard = false,
        groupByRank = groupByRank,
        discardHidesCardsFace = false,
        revealLastDeckCard = false,
        autoDeal = autoDeal,
        playersOrderClockwise = clockwise
    )
}