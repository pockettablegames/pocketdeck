package com.meltingsource.playwithme.api.session

import kotlinx.serialization.Serializable

@Serializable
sealed interface SessionMessage {

    @Serializable
    data class JoinRequest(val name: String) : SessionMessage

    @Serializable
    data class JoinApproved(val playerId: String) : SessionMessage

    @Serializable
    data class JoinRejected(val reason: String) : SessionMessage

    @Serializable
    data class SessionUpdate(val state: SessionState) : SessionMessage

    @Serializable
    data class StartGame(val gameId: String) : SessionMessage
}