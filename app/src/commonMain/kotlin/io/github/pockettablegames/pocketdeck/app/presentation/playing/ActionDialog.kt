package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.pockettablegames.pocketdeck.app.presentation.playerswitch.ActionUi
import io.github.pockettablegames.pocketdeck.app.theme.Theme

@Composable
fun ActionDialog(
    actionUi: ActionUi,
    showDialog: MutableState<Boolean>
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = {
            showDialog.value = false
        }) {
            Card(
                modifier = Modifier
                    .size(250.dp, 150.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    StackedCards(
                        actionUi.cards,
                        Modifier
                            .fillMaxWidth()
                            .height(Theme.Card.height)
                    ) {
                        PlayingCard(
                            card = it,
                        )
                    }
                }
            }
        }
    }
}