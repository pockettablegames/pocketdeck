import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meltingsource.playwithme.games.cards.Card

@Composable
fun PlayingCard(
    card: Card,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .requiredWidth(48.dp)
            .requiredHeight(64.dp)
            .then(
                if (onClick != null)
                    Modifier.clickable { onClick() }
                else Modifier
            ),
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        color = Color.White
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            Text("${card.rank}${card.suit}")
        }
    }
}