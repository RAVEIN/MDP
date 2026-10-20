package com.example.mdp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment : Fragment(R.layout.fragment_game) {

    private lateinit var gameView: GameView
    private lateinit var textScore: TextView
    private lateinit var textTime: TextView
    private lateinit var textHits: TextView
    private lateinit var textMisses: TextView

    private lateinit var gameContainer: View
    private lateinit var resultContainer: View

    private lateinit var textResultScore: TextView
    private lateinit var textResultHits: TextView
    private lateinit var textResultMisses: TextView
    private lateinit var textResultAccuracy: TextView

    private lateinit var buttonReplay: Button

    private var timer: CountDownTimer? = null

    private var score = 0
    private var hits = 0
    private var misses = 0

    private val missPenalty = 5

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        gameView =
            view.findViewById(R.id.gameView)

        textScore =
            view.findViewById(R.id.textScore)

        textTime =
            view.findViewById(R.id.textTime)

        textHits =
            view.findViewById(R.id.textHits)

        textMisses =
            view.findViewById(R.id.textMisses)

        gameContainer =
            view.findViewById(R.id.gameContainer)

        resultContainer =
            view.findViewById(R.id.resultContainer)

        textResultScore =
            view.findViewById(R.id.textResultScore)

        textResultHits =
            view.findViewById(R.id.textResultHits)

        textResultMisses =
            view.findViewById(R.id.textResultMisses)

        textResultAccuracy =
            view.findViewById(R.id.textResultAccuracy)

        buttonReplay =
            view.findViewById(R.id.buttonReplay)


        gameView.onBugHit = { bug ->

            hits++

            score += bug.points

            updateHud()
        }


        gameView.onMiss = {

            misses++

            score = maxOf(
                0,
                score - missPenalty
            )

            updateHud()
        }

        buttonReplay.setOnClickListener {
            startRound()
        }


        startRound()
    }
    private fun startRound() {

        timer?.cancel()

        score = 0
        hits = 0
        misses = 0

        updateHud()


        gameContainer.visibility =
            View.VISIBLE

        resultContainer.visibility =
            View.GONE

        val settings =
            GameSettingsStorage(
                requireContext()
            ).load()


        gameView.startGame(
            bugCount =
                settings.maxBugs,

            speedMultiplier =
                settings.gameSpeed / 5f,

            bonusIntervalSeconds =
                settings.bonusInterval
        )


        startTimer(
            settings.roundDuration
        )
    }

    private fun startTimer(
        seconds: Int
    ) {

        timer?.cancel()

        timer = object : CountDownTimer(
            seconds * 1000L,
            1000L
        ) {

            override fun onTick(
                millisUntilFinished: Long
            ) {

                val secondsLeft =
                    millisUntilFinished /
                            1000L

                textTime.text =
                    "Время: $secondsLeft"
            }


            override fun onFinish() {

                textTime.text =
                    "Время: 0"

                finishGame()
            }
        }.start()
    }

    private fun finishGame() {

        timer?.cancel()

        gameView.stopGame()

        val attempts =
            hits + misses

        val accuracy =
            if (attempts == 0) {
                0
            } else {
                hits * 100 / attempts
            }


        textResultScore.text =
            "Очки: $score"

        textResultHits.text =
            "Попадания: $hits"

        textResultMisses.text =
            "Промахи: $misses"

        textResultAccuracy.text =
            "Точность: $accuracy%"


        gameContainer.visibility =
            View.GONE

        resultContainer.visibility =
            View.VISIBLE
    }


    private fun updateHud() {

        textScore.text =
            "Очки: $score"

        textHits.text =
            "Попадания: $hits"

        textMisses.text =
            "Промахи: $misses"
    }


    override fun onDestroyView() {

        timer?.cancel()

        gameView.stopGame()

        super.onDestroyView()
    }
}