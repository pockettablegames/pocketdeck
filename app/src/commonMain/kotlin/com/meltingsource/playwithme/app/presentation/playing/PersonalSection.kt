package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.app.theme.rememberAvatars
import com.meltingsource.playwithme.games.cards.Card
import org.jetbrains.compose.resources.painterResource
import kotlin.math.min

@Composable
fun PersonalSection(
    player: PlayerSummaryUi,
    hand: List<Card>,
    tricks: List<List<Card>>,
    onPlay: (String) -> Unit,
    onCollectTrick: () -> Unit,
    modifier: Modifier = Modifier,
    maxWidth: Dp
) {
    val avatars = rememberAvatars()

    Column (
        modifier = modifier.padding(Theme.Spacing.medium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.Spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .shadow(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(avatars[player.avatar]),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                Spacer(Modifier.height(Theme.Spacing.small))
                Text(player.name)
            }

            TricksView(
                tricks.size,
                Modifier
                    .clickable(
                        onClick = onCollectTrick
                    )
            )
        }

        Spacer(Modifier.height(Theme.Spacing.small))

        Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(Theme.Card.height + 16.dp * 2),
                shape = MaterialTheme.shapes.medium,
                color = Theme.LightColorScheme.primaryContainer
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.small),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(
                        items = hand,
                        key = { it.id }
                    ) { card ->
                        PlayingCard(
                            card = card,
                            modifier = Modifier
                                .shadow(
                                    elevation = 1.dp,
                                    shape = Theme.Card.shape
                                )
                                .animateItem(),
                            onClick = { onPlay(card.id) }
                        )
                    }
                }
            }
    }
}
