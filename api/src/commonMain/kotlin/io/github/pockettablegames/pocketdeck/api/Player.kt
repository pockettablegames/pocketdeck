package io.github.pockettablegames.pocketdeck.api

import kotlinx.serialization.Serializable

@Serializable
data class Player(
    val id: String,
    val name: String,
    val avatar: Int
)