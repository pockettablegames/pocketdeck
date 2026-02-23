import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.games.cards.Card
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun TableSection(
    table: List<Card>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(224.dp),
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val list = remember {
                List(12) { it }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(144.dp)
            ) {
                items(
                    items = list,
                    key = { table.getOrNull(it)?.id ?: Uuid.random().toString() }
                ) { cardIndex ->
                    val card = table.getOrNull(cardIndex)
                    if (card != null) {
                        PlayingCard(
                            card = card,
                            modifier = Modifier.animateItem(),
                        )
                    } else {
                        Box(Modifier.size(48.dp, 64.dp))
                    }
                }
            }
            table.lastOrNull()?.let {
                PlayingCard(
                    card = it,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )
            }
        }
    }
}