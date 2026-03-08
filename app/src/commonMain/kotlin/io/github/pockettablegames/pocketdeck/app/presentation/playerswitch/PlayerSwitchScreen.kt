package io.github.pockettablegames.pocketdeck.app.presentation.playerswitch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.github.pockettablegames.pocketdeck.app.presentation.playing.PlayerSummaryUi
import io.github.pockettablegames.pocketdeck.app.theme.rememberAvatars
import org.jetbrains.compose.resources.painterResource

@Composable
fun PlayerSwitchScreen(
    players: List<PlayerSummaryUi>,
    activePlayerId: String,
    onSelectPlayer: (String) -> Unit,
    onCancel: () -> Unit
) {
    val avatars = rememberAvatars()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text(
            "Select Player",
            style = MaterialTheme.typography.headlineMedium
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {

            items(players) { player ->

                val isActive = player.id == activePlayerId

                Surface(
                    onClick = { onSelectPlayer(player.id) },
                    shape = RoundedCornerShape(16.dp),
                    tonalElevation = if (isActive) 2.dp else 0.dp,
                    border = if (isActive)
                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                    else
                        null
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .shadow(2.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(avatars[player.avatar]),
                                contentDescription = null,
                                modifier = Modifier.size(52.dp)
                            )
                        }

                        Text(player.name)

                        if (isActive) {
                            Text(
                                "Current",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }

        OutlinedButton(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel")
        }
    }
}