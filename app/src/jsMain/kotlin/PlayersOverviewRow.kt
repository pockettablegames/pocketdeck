import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlayersOverviewRow(
    players: List<PlayerSummaryUi>,
    activePlayerId: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        players.forEach { player ->

            val isActive = player.id == activePlayerId

            if (!isActive) {
                Surface(
                    tonalElevation = 0.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(player.name)
                        Text("Cards: ${player.handCount}")
                        Text("Tricks: ${player.trickCount}")
                    }
                }
            }
        }
    }
}