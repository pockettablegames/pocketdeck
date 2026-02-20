package com.meltingsource.playwithme.api.transport

import kotlinx.serialization.Serializable

@Serializable
enum class PacketType {
    SESSION,
    GAME,
    SYSTEM
}