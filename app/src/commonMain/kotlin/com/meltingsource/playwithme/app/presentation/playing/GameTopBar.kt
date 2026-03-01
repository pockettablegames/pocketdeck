package com.meltingsource.playwithme.app.presentation.playing

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameTopBar(
    onUndo: () -> Unit,
    onSwitch: () -> Unit
) {
    TopAppBar(
        title = { Text("Cards") },
        actions = {
            TextButton(onClick = onUndo) {
                Text("Undo")
            }

            TextButton(onClick = onSwitch) {
                Text("Switch")
            }
        }
    )
}