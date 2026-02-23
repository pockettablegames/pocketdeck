import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.games.cards.Card

@Composable
fun PersonalSection(
    playerName: String,
    hand: List<Card>,
    tricks: List<Card>,
    onPlay: (String) -> Unit,
    onCollectTrick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Text(playerName)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .height(168.dp),
                tonalElevation = 1.dp,
                shape = MaterialTheme.shapes.medium
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(
                        items = hand,
                        key = { it.id }
                    ) { card ->

                        PlayingCard(
                            card = card,
                            modifier = Modifier.animateItem(),
                            onClick = { onPlay(card.id) }
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(64.dp, 80.dp),
                tonalElevation = 1.dp,
                shape = MaterialTheme.shapes.medium,
                onClick = onCollectTrick
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text("Tricks (${tricks.size})", style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}