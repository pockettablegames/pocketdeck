package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.meltingsource.playwithme.app.theme.Theme
import org.jetbrains.compose.resources.painterResource
import playwithme.app.generated.resources.Res
import playwithme.app.generated.resources.avatar_0
import playwithme.app.generated.resources.avatar_1
import playwithme.app.generated.resources.avatar_2
import playwithme.app.generated.resources.avatar_3
import playwithme.app.generated.resources.avatar_4
import playwithme.app.generated.resources.avatar_5
import playwithme.app.generated.resources.playing_cards
import playwithme.app.generated.resources.tricks
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun PlayersOverviewRow(
    players: List<PlayerSummaryUi>,
    maxWidth: Dp
) {
    val avatars = remember {
        listOf(
            Res.drawable.avatar_0,
            Res.drawable.avatar_1,
            Res.drawable.avatar_2,
            Res.drawable.avatar_3,
            Res.drawable.avatar_4,
            Res.drawable.avatar_5
        )
    }

    val slot = remember(players, maxWidth) {
        (maxWidth - Theme.Spacing.medium * (players.size + 3)) / players.size.toFloat()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2 * Theme.Spacing.medium, vertical = Theme.Spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(Theme.Spacing.medium),
    ) {
        players.forEach { player ->
            Box(
                Modifier
                    .width(slot),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .shadow(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(avatars[player.avatar]),
                            contentDescription = null
                        )
                    }
                    Text(player.name)
                    Spacer(Modifier.height(Theme.Spacing.small))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            "${player.handCount}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            painter = painterResource(Res.drawable.playing_cards),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            "${player.trickCount}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Icon(
                            painter = painterResource(Res.drawable.tricks),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}