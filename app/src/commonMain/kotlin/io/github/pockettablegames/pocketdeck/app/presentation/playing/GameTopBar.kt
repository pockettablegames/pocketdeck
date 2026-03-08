package io.github.pockettablegames.pocketdeck.app.presentation.playing

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    showDeal: Boolean,
    undoEnabled: Boolean,
    onUndo: () -> Unit,
    onSwitch: () -> Unit,
    onDeal: () -> Unit,
    onEndGame: () -> Unit
) {
    TopAppBar(
        title = { Text("Cards") },
        actions = {
            if (showDeal) {
                TextButton(onClick = onDeal) {
                    Text("Deal")
                }
            }

            TextButton(
                enabled = undoEnabled,
                onClick = onUndo
            ) {
                Text("Undo")
            }

            TextButton(onClick = onSwitch) {
                Text("Switch")
            }

            var expanded by remember { mutableStateOf(false) }

            TextButton(onClick = { expanded = true }) {
                Text("Menu")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                DropdownMenuItem(
                    text = { Text("End Game") },
                    onClick = {
                        expanded = false
                        onEndGame()
                    }
                )
            }
        }
    )
}