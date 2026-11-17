package com.example.mdp

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    var score = 0
        private set

    var hits = 0
        private set

    var misses = 0
        private set

    var isRoundRunning = false
        private set

    var isRoundFinished = false
        private set

    var remainingSeconds = 0
        private set

    val accuracy: Int
        get() {
            val attempts = hits + misses

            return if (attempts == 0) {
                0
            } else {
                hits * 100 / attempts
            }
        }

    fun startRound(
        durationSeconds: Int
    ) {
        resetStats()

        remainingSeconds =
            durationSeconds

        isRoundRunning = true
        isRoundFinished = false
    }

    fun updateRemainingSeconds(
        seconds: Int
    ) {
        remainingSeconds =
            seconds.coerceAtLeast(0)
    }

    fun finishRound() {
        remainingSeconds = 0
        isRoundRunning = false
        isRoundFinished = true
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