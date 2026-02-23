import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.games.cards.Card

@Composable
fun DeckDiscardRow(
    deckCount: Int,
    discard: List<Card>,
    onDraw: () -> Unit,
    onCollectDiscard: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Surface(
            modifier = Modifier.size(64.dp, 80.dp),
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.medium,
            onClick = onDraw
        ) {
            Box(modifier = Modifier
                .padding(8.dp)
            ) {
                Text("Deck ($deckCount)", style = MaterialTheme.typography.labelSmall)
            }
        }

        Surface(
            modifier = Modifier.size(64.dp, 80.dp),
            tonalElevation = 1.dp,
            shape = MaterialTheme.shapes.medium,
            onClick = onCollectDiscard
        ) {
            Box(modifier = Modifier.padding(8.dp)) {
                Text("Discard (${discard.size})", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}