package io.github.pockettablegames.pocketdeck.api.transport

interface Transport {
    fun send(packet: Packet)
    fun onPacket(handler: (Packet) -> Unit)
}