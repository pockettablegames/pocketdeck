package com.meltingsource.playwithme.api.transport

import kotlinx.serialization.Serializable

@Serializable
data class Packet(
    val type: PacketType,
    val payload: String
)