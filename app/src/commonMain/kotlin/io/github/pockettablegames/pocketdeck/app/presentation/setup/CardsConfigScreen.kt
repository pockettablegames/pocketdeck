package io.github.pockettablegames.pocketdeck.app.presentation.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.games.cards.Card
import io.github.pockettablegames.pocketdeck.games.cards.CardsConfig
import io.github.pockettablegames.pocketdeck.games.cards.DeckType
import io.github.pockettablegames.pocketdeck.games.cards.DiscardType
import io.github.pockettablegames.pocketdeck.games.cards.Suit

@Composable
fun CardsConfigScreen(
    defaultConfig: CardsConfig,
    playersCount: Int,
    onStart: (CardsConfig) -> Unit
) {
    var distributeAllDeckCards by remember { mutableStateOf(defaultConfig.distributeAllDeckCards) }
    var cardsPerPlayer by remember { mutableStateOf(defaultConfig.cardsPerPlayer) }
    var clockwise by remember { mutableStateOf(defaultConfig.playersOrderClockwise) }
    var autoDeal by remember { mutableStateOf(defaultConfig.autoDeal) }
    val showDeck = remember { mutableStateOf(defaultConfig.showDeckZone) }
    val showDiscard = remember { mutableStateOf(defaultConfig.showDiscardZone) }
    var showPlayerTrickZone by remember { mutableStateOf(defaultConfig.showPlayerTrickZone) }
    var refillDeckWithDiscard by remember { mutableStateOf(defaultConfig.refillDeckWithDiscard) }
    var groupByRank by remember { mutableStateOf(defaultConfig.groupByRank) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Game Setup", style = MaterialTheme.typography.headlineMedium)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Deal all cards")
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = distributeAllDeckCards,
                        onCheckedChange = { distributeAllDeckCards = it }
                    )
                }
            }

            item {
                Column {
                    Text("Cards per player")
                    Slider(
                        value = cardsPerPlayer.toFloat(),
                        enabled = !distributeAllDeckCards,
                        onValueChange = { cardsPerPlayer = it.toInt() },
                        valueRange = 1f..26f
                    )
                    Text("$cardsPerPlayer cards")
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Auto deal")
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = autoDeal,
                        onCheckedChange = { autoDeal = it }
                    )
                }
            }

            item {
                Column {
                    Text("Deck zone")
                    Dropdown(
                        items = listOf(
                            "Hidden" to DeckType.HIDDEN,
                            "Face down" to DeckType.FACE_DOWN,
                            "Last up" to DeckType.FACE_DOWN_LAST_UP
                        ),
                        state = showDeck,
                    )
                }
            }

            item {
                Column {
                    Text("Discard zone")
                    Dropdown(
                        items = listOf(
                            "Hidden" to DiscardType.HIDDEN,
                            "Face down" to DiscardType.FACE_DOWN,
                            "Face up" to DiscardType.FACE_UP
                        ),
                        state = showDiscard,
                    )
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Show tricks zone")
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = showPlayerTrickZone,
                        onCheckedChange = { showPlayerTrickZone = it }
                    )
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Refill deck with discard")
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = refillDeckWithDiscard,
                        onCheckedChange = { refillDeckWithDiscard = it }
                    )
                }
            }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Group By Rank")
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = groupByRank,
                        onCheckedChange = { groupByRank = it }
                    )
                }
            }
        }
        Button(
            onClick = {
                onStart(
                    defaultCardsConfig(
                        distributeAllDeckCards = distributeAllDeckCards,
                        cardsPerPlayer = cardsPerPlayer,
                        clockwise = clockwise,
                        autoDeal = autoDeal,
                        showDeck = showDeck.value,
                        showDiscard = showDiscard.value,
                        groupByRank = groupByRank,
                        showPlayerTricksZone = showPlayerTrickZone,
                        refillDeckWithDiscard = refillDeckWithDiscard
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

@Composable
private fun <T> Dropdown(
    items: List<Pair<String, T>>,
    state: MutableState<T>
) {
    var expanded by remember { mutableStateOf(false) }

    val selected = remember(state.value, items) {
        items.firstOrNull { it.second == state.value }?.first ?: ""
    }

    Button(onClick = { expanded = true }) {
        Text(selected)
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        items.forEach { (text, value) ->
            DropdownMenuItem(
                text = { Text(text) },
                onClick = {
                    expanded = false
                    state.value = value
                }
            )
        }
    }
}

fun defaultCardsConfig(
    distributeAllDeckCards: Boolean,
    cardsPerPlayer: Int,
    clockwise: Boolean,
    autoDeal: Boolean,
    showDeck: DeckType,
    showDiscard: DiscardType,
    showPlayerTricksZone: Boolean,
    refillDeckWithDiscard: Boolean,
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
        },
        distributeAllDeckCards = distributeAllDeckCards,
        cardsPerPlayer = cardsPerPlayer,
        showDeckZone = showDeck,
        showDiscardZone = showDiscard,
        showPlayerTrickZone = showPlayerTricksZone,
        refillDeckWithDiscard = refillDeckWithDiscard,
        groupByRank = groupByRank,
        autoDeal = autoDeal,
        playersOrderClockwise = clockwise
    )
}