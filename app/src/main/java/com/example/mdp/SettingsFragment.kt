package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

private lateinit var storage:
        GameSettingsStorage

class SettingsFragment :
    Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        storage =
            GameSettingsStorage(
                requireContext()
            )

        val seekGameSpeed =
            view.findViewById<SeekBar>(
                R.id.seekGameSpeed
            )

        val textGameSpeed =
            view.findViewById<TextView>(
                R.id.textGameSpeed
            )

        val seekMaxBugs =
            view.findViewById<SeekBar>(
                R.id.seekMaxBugs
            )

        val textMaxBugs =
            view.findViewById<TextView>(
                R.id.textMaxBugs
            )

        val seekBonusInterval =
            view.findViewById<SeekBar>(
                R.id.seekBonusInterval
            )

        val textBonusInterval =
            view.findViewById<TextView>(
                R.id.textBonusInterval
            )

        val seekRoundDuration =
            view.findViewById<SeekBar>(
                R.id.seekRoundDuration
            )

        val textRoundDuration =
            view.findViewById<TextView>(
                R.id.textRoundDuration
            )

        val savedSettings =
            storage.load()

        seekGameSpeed.progress =
            savedSettings.gameSpeed

        seekMaxBugs.progress =
            savedSettings.maxBugs

        seekBonusInterval.progress =
            savedSettings.bonusInterval

        seekRoundDuration.progress =
            savedSettings.roundDuration


        setupSeekBar(
            seekGameSpeed
        ) { value ->

            textGameSpeed.text =
                "Скорость игры: $value"

            saveSettings(
                gameSpeed = value,
                maxBugs = seekMaxBugs.progress,
                bonusInterval = seekBonusInterval.progress,
                roundDuration = seekRoundDuration.progress
            )
        }


        setupSeekBar(
            seekMaxBugs
        ) { value ->

            textMaxBugs.text =
                "Максимум тараканов: $value"

            saveSettings(
                gameSpeed = seekGameSpeed.progress,
                maxBugs = value,
                bonusInterval = seekBonusInterval.progress,
                roundDuration = seekRoundDuration.progress
            )
        }


        setupSeekBar(
            seekBonusInterval
        ) { value ->

            textBonusInterval.text =
                "Интервал бонусов: $value сек."

            saveSettings(
                gameSpeed = seekGameSpeed.progress,
                maxBugs = seekMaxBugs.progress,
                bonusInterval = value,
                roundDuration = seekRoundDuration.progress
            )
        }


        setupSeekBar(
            seekRoundDuration
        ) { value ->

            textRoundDuration.text =
                "Длительность раунда: $value сек."

            saveSettings(
                gameSpeed = seekGameSpeed.progress,
                maxBugs = seekMaxBugs.progress,
                bonusInterval = seekBonusInterval.progress,
                roundDuration = value
            )
        }
    }


    private fun saveSettings(
        gameSpeed: Int,
        maxBugs: Int,
        bonusInterval: Int,
        roundDuration: Int
    ) {

        storage.save(
            GameSettings(
                gameSpeed = gameSpeed,
                maxBugs = maxBugs,
                bonusInterval = bonusInterval,
                roundDuration = roundDuration
            )
        )
    }


    private fun setupSeekBar(
        seekBar: SeekBar,
        onChange: (Int) -> Unit
    ) {

        onChange(
            seekBar.progress
        )

        seekBar.setOnSeekBarChangeListener(

            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {

                    onChange(
                        progress
                    )
                }


                override fun onStartTrackingTouch(
                    seekBar: SeekBar?
                ) {
                }


                override fun onStopTrackingTouch(
                    seekBar: SeekBar?
                ) {
                }
            }
        )
    }
}