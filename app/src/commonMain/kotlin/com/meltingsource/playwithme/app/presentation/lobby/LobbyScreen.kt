package com.meltingsource.playwithme.app.presentation.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.api.session.SessionState

@Composable
fun LobbyScreen(
    sessionState: SessionState,
    onAddPlayer: (String) -> Unit,
    onStart: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            Text("Lobby", style = MaterialTheme.typography.headlineMedium)
        }

        items(sessionState.players) { player ->
            Text(player.name)
        }

        item {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Player name") }
            )
        }

        item {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onAddPlayer(name)
                    name = ""
                }
            }) {
                Text("Add Player")
            }
        }

        item {
            Button(
                onClick = onStart,
                enabled = sessionState.players.isNotEmpty()
            ) {
                Text("Start Game")
            }
        }
    }
}
