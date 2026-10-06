package com.example.mdp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class GameFragment :
    Fragment(R.layout.fragment_game) {

    private lateinit var gameView:
            GameView


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )

        gameView =
            view.findViewById(
                R.id.gameView
            )

        gameView.startGame()
    }


    override fun onDestroyView() {
        gameView.stopGame()

        super.onDestroyView()
    }
}