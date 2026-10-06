package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class GameFragment :
    Fragment(R.layout.fragment_game) {

    private lateinit var gameView: GameView

    private var score = 0
    private var hits = 0
    private var misses = 0


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )


        val textScore =
            view.findViewById<TextView>(
                R.id.textScore
            )

        val textHits =
            view.findViewById<TextView>(
                R.id.textHits
            )

        val textMisses =
            view.findViewById<TextView>(
                R.id.textMisses
            )


        gameView =
            view.findViewById(
                R.id.gameView
            )


        /*
         * Обработка попадания по жуку.
         */
        gameView.onBugHit = { bug ->

            hits++

            score += bug.points

            textScore.text =
                "Очки: $score"

            textHits.text =
                "Попадания: $hits"
        }


        /*
         * Обработка промаха.
         */
        gameView.onMiss = {

            misses++

            score =
                maxOf(
                    0,
                    score - 5
                )

            textScore.text =
                "Очки: $score"

            textMisses.text =
                "Промахи: $misses"
        }


        // Запускаем игру
        gameView.startGame()
    }


    override fun onDestroyView() {

        gameView.stopGame()

        super.onDestroyView()
    }
}