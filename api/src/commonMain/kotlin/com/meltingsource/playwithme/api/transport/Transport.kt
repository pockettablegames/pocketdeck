package com.meltingsource.playwithme.api.transport

interface Transport {
    fun send(packet: Packet)
    fun onPacket(handler: (Packet) -> Unit)
}