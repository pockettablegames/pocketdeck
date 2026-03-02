package io.github.pockettablegames.pocketdeck.app.presentation.score

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.api.Player
import io.github.pockettablegames.pocketdeck.app.presentation.playing.PlayingCard
import io.github.pockettablegames.pocketdeck.games.cards.Card

@Composable
fun ScoreEntryScreen(
    players: List<Player>,
    activePlayer: Player,
    tricks: Map<String, List<List<Card>>>,
    currentScore: Int?,
    onScoreChange: (String, Int) -> Unit,
    onConfirmPlayerScore: () -> Unit,
    onSwitchPlayer: () -> Unit
) {

    val playerTricks = tricks[activePlayer.id] ?: emptyList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Top bar
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Score Entry",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.weight(1f))

            TextButton(onClick = onSwitchPlayer) {
                Text("Switch Player")
            }
        }

        // Active player
        Text(
            activePlayer.name,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            "${playerTricks.size} tricks collected",
            style = MaterialTheme.typography.bodyMedium
        )

        HorizontalDivider()

        // Tricks list
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(playerTricks) { index, trick ->

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {

                    Text(
                        "Trick ${index + 1}",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        trick.forEach { card ->
                            PlayingCard(card = card)
                        }
                    }
                }
            }
        }

        // Score input
        OutlinedTextField(
            value = currentScore?.toString() ?: "",
            onValueChange = {
                onScoreChange(activePlayer.id, it.toInt())
            },
            label = { Text("Score") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onConfirmPlayerScore,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to results")
        }
    }
}