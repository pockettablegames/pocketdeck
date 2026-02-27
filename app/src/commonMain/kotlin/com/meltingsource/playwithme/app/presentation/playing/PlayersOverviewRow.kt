package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import com.meltingsource.playwithme.app.theme.Theme
import com.meltingsource.playwithme.app.theme.rememberAvatars
import org.jetbrains.compose.resources.painterResource
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun PlayersOverviewRow(
    players: List<PlayerSummaryUi>,
    maxWidth: Dp
) {
    val avatars = rememberAvatars()

    val slot = remember(players, maxWidth) {
        (maxWidth - Theme.Spacing.medium * (players.size + 3)) / players.size.toFloat()
    }

    val stackWidth = remember(slot) {
        min(slot,maxWidth * 0.5f)
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
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
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
                        if(player.trickCount > 0) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(
                                        x = (6).dp,
                                        y = (-6).dp
                                    )
                                    .size(24.dp)
                                    .pointerInput(Unit) { }
                            ) {
                                Text(player.trickCount.toString())
                            }
                        }
                    }
                    Spacer(Modifier.height(Theme.Spacing.small))
                    Text(player.name)
                    Spacer(Modifier.height(Theme.Spacing.small))
                    StackedCards(
                        List(player.handCount) { it },
                        Modifier
                            .width(stackWidth)
                            .height(Theme.Card.height * 0.7f),
                        cardWidth = Theme.Card.width * 0.7f
                    ) {
                        CardBack(
                            Modifier.size(
                                Theme.Card.width * 0.7f,
                                Theme.Card.height * 0.7f
                            )
                        )
                    }
                }
            }
        }
    }
}