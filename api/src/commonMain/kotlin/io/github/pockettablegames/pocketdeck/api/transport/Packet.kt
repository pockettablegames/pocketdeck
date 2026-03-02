package io.github.pockettablegames.pocketdeck.api.transport

import kotlinx.serialization.Serializable

@Serializable
data class Packet(
    val type: PacketType,
    val payload: String
)