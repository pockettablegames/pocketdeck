package io.github.pockettablegames.pocketdeck.app.presentation.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.api.session.SessionState

@Composable
fun LobbyScreen(
    sessionState: SessionState,
    onAddPlayer: (String) -> Unit,
    onRemovePlayer: (String) -> Unit,
    onContinue: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            "Pocket Deck",
            style = MaterialTheme.typography.headlineMedium
        )

        // Players Section
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(
                "Players (${sessionState.players.size})",
                style = MaterialTheme.typography.titleMedium
            )

            sessionState.players.forEach { player ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(player.name)
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        onClick = { onRemovePlayer(player.id) }
                    ) {
                        Text("Remove")
                    }
                }
            }
        }

        // Add Player Section
        if(sessionState.players.size < 6) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Player name") },
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            onAddPlayer(name.trim())
                            name = ""
                        }
                    }
                ) {
                    Text("Add")
                }
            }
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onContinue,
            enabled = sessionState.players.size >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}