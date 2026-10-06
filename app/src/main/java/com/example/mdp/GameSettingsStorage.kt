package com.example.mdp

import android.content.Context

class GameSettingsStorage(
    context: Context
) {
    private val preferences =
        context.getSharedPreferences(
            "game_settings",
            Context.MODE_PRIVATE
        )
    fun load(): GameSettings{

        return GameSettings(

            gameSpeed = preferences.getInt(
                "game_speed",
                5
            ),
            maxBugs = preferences.getInt(
                "max_bugs",
                10
            ),
            bonusInterval = preferences.getInt(
                "bonus_interval",
                15
            ),
            roundDuration = preferences.getInt(
                "round_duration",
                60
            )
        )
    }
    fun save(
        settings: GameSettings
    ) {
        preferences.edit()
            .putInt(
            "game_speed",
            settings.gameSpeed
        )
            .putInt(
                "max_bugs",
                settings.maxBugs
            )
            .putInt(
                "bonus_interval",
                settings.bonusInterval
            )
            .putInt(
                "round_duration",
                settings.roundDuration
            )
            .apply()
    }
}