package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mdp.data.AppDatabase
import com.example.mdp.data.PlayerEntity
import kotlinx.coroutines.launch
import java.util.Calendar

class RegistrationFragment :
    Fragment(
        R.layout.fragment_registration
    ) {

    private var birthDay = 1
    private var birthMonth = 1
    private var birthYear = 2000


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )


        val database =
            AppDatabase.getInstance(
                requireContext()
            )


        val currentPlayerStorage =
            CurrentPlayerStorage(
                requireContext()
            )


        val spinnerExistingPlayer =
            view.findViewById<Spinner>(
                R.id.spinnerExistingPlayer
            )


        val buttonSelectPlayer =
            view.findViewById<Button>(
                R.id.buttonSelectPlayer
            )


        val textCurrentPlayer =
            view.findViewById<TextView>(
                R.id.textCurrentPlayer
            )


        val editFullName =
            view.findViewById<EditText>(
                R.id.editFullName
            )


        val radioGender =
            view.findViewById<RadioGroup>(
                R.id.radioGender
            )


        val spinnerCourse =
            view.findViewById<Spinner>(
                R.id.spinnerCourse
            )


        val seekDifficulty =
            view.findViewById<SeekBar>(
                R.id.seekDifficulty
            )


        val textDifficulty =
            view.findViewById<TextView>(
                R.id.textDifficulty
            )


        val calendarBirth =
            view.findViewById<CalendarView>(
                R.id.calendarBirth
            )


        val buttonRegister =
            view.findViewById<Button>(
                R.id.buttonRegister
            )


        val textResult =
            view.findViewById<TextView>(
                R.id.textResult
            )


        val imageZodiac =
            view.findViewById<ImageView>(
                R.id.imageZodiac
            )


        // Курсы

        val courses = arrayOf(
            "1 курс",
            "2 курс",
            "3 курс",
            "4 курс"
        )


        val courseAdapter =
            ArrayAdapter(
                requireContext(),

                android.R.layout
                    .simple_spinner_item,

                courses
            )


        courseAdapter
            .setDropDownViewResource(
                android.R.layout
                    .simple_spinner_dropdown_item
            )


        spinnerCourse.adapter =
            courseAdapter


        // Дата рождения

        val calendar =
            Calendar.getInstance()


        birthDay =
            calendar.get(
                Calendar.DAY_OF_MONTH
            )


        birthMonth =
            calendar.get(
                Calendar.MONTH
            ) + 1


        birthYear =
            calendar.get(
                Calendar.YEAR
            )


        calendarBirth.date =
            System.currentTimeMillis()


        calendarBirth.maxDate =
            System.currentTimeMillis()


        calendarBirth
            .setOnDateChangeListener {
                    _,
                    year,
                    month,
                    dayOfMonth ->

                birthYear =
                    year

                birthMonth =
                    month + 1

                birthDay =
                    dayOfMonth
            }


        // Сложность

        seekDifficulty
            .setOnSeekBarChangeListener(

                object :
                    SeekBar
                    .OnSeekBarChangeListener {


                    override fun
                            onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {

                        textDifficulty.text =
                            "Сложность: $progress"
                    }


                    override fun
                            onStartTrackingTouch(
                        seekBar: SeekBar?
                    ) {
                    }


                    override fun
                            onStopTrackingTouch(
                        seekBar: SeekBar?
                    ) {
                    }
                }
            )


        var existingPlayers:
                List<PlayerEntity> =
            emptyList()


        fun showCurrentPlayer(
            player: PlayerEntity?
        ) {

            textCurrentPlayer.text =

                if (player == null) {

                    "Игрок не выбран"

                } else {

                    "${player.fullName}, " +
                            "сложность " +
                            "${player.difficulty}"
                }
        }


        fun loadPlayers(
            preferredPlayerId:
            Long? =
                currentPlayerStorage
                    .getPlayerId()
        ) {

            viewLifecycleOwner
                .lifecycleScope
                .launch {


                    existingPlayers =

                        database.playerDao()
                            .getAllPlayers()


                    val names =

                        if (
                            existingPlayers
                                .isEmpty()
                        ) {

                            listOf(
                                "Нет зарегистрированных игроков"
                            )

                        } else {

                            existingPlayers.map {
                                it.fullName
                            }
                        }


                    val adapter =
                        ArrayAdapter(

                            requireContext(),

                            android.R.layout
                                .simple_spinner_item,

                            names
                        )


                    adapter
                        .setDropDownViewResource(

                            android.R.layout
                                .simple_spinner_dropdown_item
                        )


                    spinnerExistingPlayer
                        .adapter =
                        adapter


                    buttonSelectPlayer.isEnabled = existingPlayers.isNotEmpty()


                    val currentPlayer =
                        preferredPlayerId ?.let { id -> existingPlayers.firstOrNull {
                                        it.id == id
                                    }
                            }
                    if (
                        currentPlayer != null
                    ) {

                        val index =

                            existingPlayers
                                .indexOfFirst {

                                    it.id ==
                                            currentPlayer.id
                                }

                        spinnerExistingPlayer
                            .setSelection(
                                index
                            )
                    }

                    showCurrentPlayer(
                        currentPlayer
                    )
                }
        }


        buttonSelectPlayer
            .setOnClickListener {


                if (
                    existingPlayers
                        .isEmpty()
                ) {

                    return@setOnClickListener
                }


                val position = spinnerExistingPlayer.selectedItemPosition


                val player = existingPlayers [position]


                currentPlayerStorage
                    .savePlayerId(
                        player.id
                    )


                showCurrentPlayer(
                    player
                )

                Toast.makeText(
                    requireContext(),

                    "Выбран игрок: " +
                            player.fullName,

                    Toast.LENGTH_SHORT
                ).show()
            }

         //Регистрация нового
        buttonRegister
            .setOnClickListener {

                val fullName =

                    editFullName.text
                        .toString()
                        .trim()

                if (
                    fullName.isEmpty()
                ) {
                    editFullName.error =
                        "Введите ФИО"

                    return@setOnClickListener
                }

                val checkedGenderId = radioGender.checkedRadioButtonId

                if (
                    checkedGenderId == -1
                ) {

                    Toast.makeText(
                        requireContext(),

                        "Выберите пол",

                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }


                val genderRadioButton =view.findViewById <RadioButton> (checkedGenderId)

                val gender = genderRadioButton
                        .text
                        .toString()

                val course = spinnerCourse
                    .selectedItem
                    .toString()

                val difficulty =  seekDifficulty.progress

                val zodiac =  getZodiacSign(
                        birthDay,
                        birthMonth
                    )

                val player =

                    PlayerRegistration(

                        fullName =
                            fullName,
                        gender =
                            gender,
                        course =
                            course,
                        difficulty =
                            difficulty,
                        birthDay =
                            birthDay,
                        birthMonth =
                            birthMonth,
                        birthYear =
                            birthYear,
                        zodiac =
                            zodiac
                    )

                textResult.text = """
                    ФИО: ${player.fullName}
                    Пол: ${player.gender}
                    Курс: ${player.course}
                    Сложность: ${player.difficulty}
                    Дата рождения: ${player.birthDay}.${player.birthMonth}.${player.birthYear}
                    Знак зодиака: ${player.zodiac}
                """.trimIndent()


                imageZodiac
                    .setImageResource(

                        getZodiacImage(
                            player.birthDay,
                            player.birthMonth
                        )
                    )


                 //Модель Room.
                val playerEntity =

                    PlayerEntity(

                        fullName =
                            player.fullName,

                        gender =
                            player.gender,

                        course =
                            player.course,

                        difficulty =
                            player.difficulty,

                        birthDay =
                            player.birthDay,

                        birthMonth =
                            player.birthMonth,

                        birthYear =
                            player.birthYear,

                        zodiac =
                            player.zodiac
                    )


                viewLifecycleOwner
                    .lifecycleScope
                    .launch {

                        val newPlayerId =

                            database.playerDao()
                                .insertPlayer(
                                    playerEntity
                                )

                        currentPlayerStorage
                            .savePlayerId(
                                newPlayerId
                            )


                        Toast.makeText(
                            requireContext(),

                            "Игрок сохранён " +
                                    "в базе данных",

                            Toast.LENGTH_SHORT
                        ).show()
                        loadPlayers(
                            newPlayerId
                        )
                    }
            }

        loadPlayers()
    }


    private fun getZodiacSign(
        day: Int,
        month: Int
    ): String {

        return when (month) {

            1 ->
                if (day <= 19)
                    "Козерог"
                else
                    "Водолей"

            2 ->
                if (day <= 18)
                    "Водолей"
                else
                    "Рыбы"

            3 ->
                if (day <= 20)
                    "Рыбы"
                else
                    "Овен"

            4 ->
                if (day <= 19)
                    "Овен"
                else
                    "Телец"

            5 ->
                if (day <= 20)
                    "Телец"
                else
                    "Близнецы"

            6 ->
                if (day <= 20)
                    "Близнецы"
                else
                    "Рак"

            7 ->
                if (day <= 22)
                    "Рак"
                else
                    "Лев"

            8 ->
                if (day <= 22)
                    "Лев"
                else
                    "Дева"

            9 ->
                if (day <= 22)
                    "Дева"
                else
                    "Весы"

            10 ->
                if (day <= 22)
                    "Весы"
                else
                    "Скорпион"

            11 ->
                if (day <= 21)
                    "Скорпион"
                else
                    "Стрелец"

            12 ->
                if (day <= 21)
                    "Стрелец"
                else
                    "Козерог"

            else ->
                "Неизвестно"
        }
    }


    private fun getZodiacImage(
        day: Int,
        month: Int
    ): Int {

        return when (
            getZodiacSign(
                day,
                month
            )
        ) {

            "Овен" ->
                R.drawable.aries

            "Телец" ->
                R.drawable.taurus

            "Близнецы" ->
                R.drawable.gemini

            "Рак" ->
                R.drawable.cancer

            "Лев" ->
                R.drawable.leo

            "Дева" ->
                R.drawable.virgo

            "Весы" ->
                R.drawable.libra

            "Скорпион" ->
                R.drawable.scorpio

            "Стрелец" ->
                R.drawable.sagittarius

            "Козерог" ->
                R.drawable.capricorn

            "Водолей" ->
                R.drawable.aquarius

            "Рыбы" ->
                R.drawable.pisces

            else ->
                android.R.drawable
                    .ic_menu_help
        }
    }
}