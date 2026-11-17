package com.example.mdp

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var score = 0
        private set

    var hits = 0
        private set

    var misses = 0
        private set

    val accuracy: Int
        get() {
            val attempts =
                hits + misses

            return if (attempts == 0) {
                0
            } else {
                hits * 100 / attempts
            }
        }

    fun resetStats() {
        score = 0
        hits = 0
        misses = 0
    }

    fun registerBugHit(
        points: Int
    ) {
        hits++
        score += points
    }

    fun registerMiss() {
        misses++

        score = maxOf(
            0,
            score - MISS_PENALTY
        )
    }

    companion object {
        private const val MISS_PENALTY = 5
    }
}