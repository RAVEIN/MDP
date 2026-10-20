package com.example.mdp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

import androidx.lifecycle.lifecycleScope
import com.example.mdp.data.AppDatabase
import com.example.mdp.data.PlayerEntity
import com.example.mdp.data.ScoreEntity
import kotlinx.coroutines.launch
import android.widget.Toast

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

    private var resultSaved = false

    private val missPenalty = 5

    private lateinit var textPlayer: TextView

    private lateinit var database: AppDatabase
    private lateinit var currentPlayerStorage: CurrentPlayerStorage

    private var activePlayer: PlayerEntity? = null

    private var loadedPlayerId: Long? = null

    private var hasGameState = false

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

        textPlayer =
            view.findViewById(R.id.textPlayer)


        database =
            AppDatabase.getInstance(
                requireContext()
            )

        currentPlayerStorage =
            CurrentPlayerStorage(
                requireContext()
            )


        gameView.onBugHit = { bug ->

            hits++

            score +=
                bug.points

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

        gameView.onAllBugsDestroyed = {

            finishGame()
        }


        buttonReplay.setOnClickListener {

            if (activePlayer == null) {

                Toast.makeText(
                    requireContext(),
                    "Сначала выберите игрока",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                startRound()
            }
        }



    }

    private fun loadActivePlayer() {

        val playerId =
            currentPlayerStorage.getPlayerId()

        if (playerId == null) {

            activePlayer = null
            loadedPlayerId = null
            hasGameState = false

            showPlayerRequired()

            return
        }

        viewLifecycleOwner
            .lifecycleScope
            .launch {

                val player =
                    database.playerDao()
                        .getPlayerById(playerId)

                if (player == null) {

                    currentPlayerStorage.clear()

                    activePlayer = null
                    loadedPlayerId = null
                    hasGameState = false

                    showPlayerRequired()

                    return@launch
                }

                val playerChanged =
                    loadedPlayerId != player.id

                activePlayer =
                    player

                loadedPlayerId =
                    player.id

                textPlayer.text =
                    "Игрок: ${player.fullName} | " +
                            "Сложность: ${player.difficulty}"

                buttonReplay.isEnabled =
                    true

                if (
                    !hasGameState ||
                    playerChanged
                ) {

                    startRound()
                }
            }
    }

    private fun showPlayerRequired() {

        timer?.cancel()

        gameView.stopGame()

        hasGameState = false

        gameContainer.visibility =
            View.GONE

        resultContainer.visibility =
            View.VISIBLE

        textResultScore.text =
            "Сначала выберите или зарегистрируйте игрока"

        textResultHits.text =
            ""

        textResultMisses.text =
            ""

        textResultAccuracy.text =
            ""

        buttonReplay.isEnabled =
            false
    }



    private fun startRound() {

        hasGameState = true

        timer?.cancel()

        score = 0
        hits = 0
        misses = 0

        resultSaved = false

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

        saveResult(accuracy)
    }

    private fun saveResult(
        accuracy: Int
    ) {

        if (resultSaved) {
            return
        }

        val player =
            activePlayer ?: return

        resultSaved = true

        val result =
            ScoreEntity(
                playerId = player.id,
                score = score,
                difficulty = player.difficulty,
                hits = hits,
                misses = misses,
                accuracy = accuracy,
                timestamp = System.currentTimeMillis()
            )

        viewLifecycleOwner
            .lifecycleScope
            .launch {

                database.scoreDao()
                    .insertScore(result)
            }
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

    override fun onResume() {
        super.onResume()

        loadActivePlayer()
    }
}
