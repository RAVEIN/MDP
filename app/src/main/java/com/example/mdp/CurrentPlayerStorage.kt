package com.example.mdp

import android.content.Context

class CurrentPlayerStorage(
    context: Context
) {

    private val preferences =
        context.getSharedPreferences(
            "current_player",
            Context.MODE_PRIVATE
        )


    fun savePlayerId(
        playerId: Long
    ) {

        preferences.edit()
            .putLong(
                "active_player_id",
                playerId
            )
            .apply()
    }


    fun getPlayerId(): Long? {

        val id =
            preferences.getLong(
                "active_player_id",
                -1L
            )


        return if (id == -1L) {
            null
        } else {
            id
        }
    }


    fun clear() {

        preferences.edit()
            .remove(
                "active_player_id"
            )
            .apply()
    }
}