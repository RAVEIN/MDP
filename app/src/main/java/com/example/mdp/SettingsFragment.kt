package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

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


        setupSeekBar(
            seekGameSpeed
        ) { value ->

            textGameSpeed.text =
                "Скорость игры: $value"
        }


        setupSeekBar(
            seekMaxBugs
        ) { value ->

            textMaxBugs.text =
                "Максимум тараканов: $value"
        }


        setupSeekBar(
            seekBonusInterval
        ) { value ->

            textBonusInterval.text =
                "Интервал бонусов: $value сек."
        }


        setupSeekBar(
            seekRoundDuration
        ) { value ->

            textRoundDuration.text =
                "Длительность раунда: $value сек."
        }
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