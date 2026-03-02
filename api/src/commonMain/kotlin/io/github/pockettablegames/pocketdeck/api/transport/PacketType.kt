package io.github.pockettablegames.pocketdeck.api.transport

import kotlinx.serialization.Serializable

@Serializable
enum class PacketType {
    SESSION,
    GAME,
    SYSTEM
}