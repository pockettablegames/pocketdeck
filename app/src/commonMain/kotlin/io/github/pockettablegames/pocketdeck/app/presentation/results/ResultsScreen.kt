package io.github.pockettablegames.pocketdeck.app.presentation.results

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.api.Player

@Composable
fun ResultScreen(
    players: List<Player>,
    results: Map<String, Int>,
    onReturnToLobby: () -> Unit,
    onPlayAgain: () -> Unit
) {
    val ranked = players
        .map { player ->
            player to (results[player.id] ?: 0)
        }
        .sortedByDescending { it.second }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            "Results",
            style = MaterialTheme.typography.headlineMedium
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

            ranked.forEachIndexed { index, (player, score) ->

                val isWinner = index == 0

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isWinner)
                                MaterialTheme.colorScheme.surfaceVariant
                            else
                                Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // Position
                    Text(
                        "${index + 1}.",
                        modifier = Modifier.width(32.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Name
                    Text(
                        player.name,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium
                    )

                    // Score
                    Text(
                        score.toString(),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedButton(
                onClick = onReturnToLobby,
                modifier = Modifier.weight(1f)
            ) {
                Text("Return to Lobby")
            }

            Button(
                onClick = onPlayAgain,
                modifier = Modifier.weight(1f)
            ) {
                Text("Play Again")
            }
        }
    }
}