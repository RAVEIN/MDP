package com.example.mdp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment :
    Fragment(R.layout.fragment_game) {

    private lateinit var gameView: GameView

    private lateinit var textScore: TextView
    private lateinit var textTime: TextView
    private lateinit var textHits: TextView
    private lateinit var textMisses: TextView

    private lateinit var gameContainer: View
    private lateinit var resultContainer: View

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



        gameView.onBugHit = { bug ->

            hits++

            score += bug.points

            updateHud()
        }


        gameView.onMiss = {

            misses++

            score =
                maxOf(
                    0,
                    score - missPenalty
                )

            updateHud()
        }


        startRound()
    }



    private fun startRound() {

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
                settings.gameSpeed / 5f
        )


        /*
         * Запускаем таймер раунда.
         */
        startTimer(
            settings.roundDuration
        )
    }

    private fun startTimer(
        seconds: Int
    ) {

        timer?.cancel()


        timer =
            object : CountDownTimer(
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

                    gameView.stopGame()

                    finishGame()
                }
            }.start()
    }


    private fun updateHud() {

        textScore.text =
            "Очки: $score"

        textHits.text =
            "Попадания: $hits"

        textMisses.text =
            "Промахи: $misses"
    }


    private fun finishGame() {

        gameView.stopGame()

    }


    override fun onDestroyView() {

        timer?.cancel()

        gameView.stopGame()

        super.onDestroyView()
    }
}