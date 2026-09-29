package com.example.mdp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private var birthDay = 1
    private var birthMonth = 1
    private var birthYear = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editFullName = findViewById<EditText>(R.id.editFullName)
        val radioGender = findViewById<RadioGroup>(R.id.radioGender)
        val spinnerCourse = findViewById<Spinner>(R.id.spinnerCourse)
        val seekDifficulty = findViewById<SeekBar>(R.id.seekDifficulty)
        val textDifficulty = findViewById<TextView>(R.id.textDifficulty)
        val calendarBirth = findViewById<CalendarView>(R.id.calendarBirth)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val textResult = findViewById<TextView>(R.id.textResult)
        val imageZodiac = findViewById<ImageView>(R.id.imageZodiac)

        // Курсы
        val courses = arrayOf(
            "1 курс",
            "2 курс",
            "3 курс",
            "4 курс"
        )

        val courseAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            courses
        )

        courseAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        spinnerCourse.adapter = courseAdapter

        // Текущая дата календаря
        val calendar = Calendar.getInstance()
        birthDay = calendar.get(Calendar.DAY_OF_MONTH)
        birthMonth = calendar.get(Calendar.MONTH) + 1
        birthYear = calendar.get(Calendar.YEAR)

        // Выбор даты рождения
        calendarBirth.setOnDateChangeListener { _, year, month, dayOfMonth ->
            birthYear = year
            birthMonth = month + 1
            birthDay = dayOfMonth
        }

        // Изменение сложности
        seekDifficulty.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    textDifficulty.text = "Сложность: $progress"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            }
        )

        buttonRegister.setOnClickListener {

            val fullName = editFullName.text.toString().trim()

            if (fullName.isEmpty()) {
                editFullName.error = "Введите ФИО"
                return@setOnClickListener
            }

            val checkedGenderId = radioGender.checkedRadioButtonId

            if (checkedGenderId == -1) {
                Toast.makeText(
                    this,
                    "Выберите пол",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val genderRadioButton =
                findViewById<RadioButton>(checkedGenderId)

            val gender = genderRadioButton.text.toString()

            val course = spinnerCourse.selectedItem.toString()
            val difficulty = seekDifficulty.progress

            val zodiac = getZodiacSign(
                birthDay,
                birthMonth
            )

            // Данные заносятся в структуру данных
            val player = PlayerRegistration(
                fullName = fullName,
                gender = gender,
                course = course,
                difficulty = difficulty,
                birthDay = birthDay,
                birthMonth = birthMonth,
                birthYear = birthYear,
                zodiac = zodiac
            )

            // Вывод данных
            textResult.text = """
                ФИО: ${player.fullName}
                Пол: ${player.gender}
                Курс: ${player.course}
                Сложность: ${player.difficulty}
                Дата рождения: ${player.birthDay}.${player.birthMonth}.${player.birthYear}
                Знак зодиака: ${player.zodiac}
            """.trimIndent()

            // Картинка знака зодиака
            imageZodiac.setImageResource(
                getZodiacImage(
                    player.birthDay,
                    player.birthMonth
                )
            )
        }
    }


    private fun getZodiacSign(day: Int, month: Int): String {

        return when (month) {

            1 ->
                if (day <= 19) "Козерог"
                else "Водолей"

            2 ->
                if (day <= 18) "Водолей"
                else "Рыбы"

            3 ->
                if (day <= 20) "Рыбы"
                else "Овен"

            4 ->
                if (day <= 19) "Овен"
                else "Телец"

            5 ->
                if (day <= 20) "Телец"
                else "Близнецы"

            6 ->
                if (day <= 20) "Близнецы"
                else "Рак"

            7 ->
                if (day <= 22) "Рак"
                else "Лев"

            8 ->
                if (day <= 22) "Лев"
                else "Дева"

            9 ->
                if (day <= 22) "Дева"
                else "Весы"

            10 ->
                if (day <= 22) "Весы"
                else "Скорпион"

            11 ->
                if (day <= 21) "Скорпион"
                else "Стрелец"

            12 ->
                if (day <= 21) "Стрелец"
                else "Козерог"

            else -> "Неизвестно"
        }
    }


    private fun getZodiacImage(day: Int, month: Int): Int {

        return when (getZodiacSign(day, month)) {

            "Овен" -> R.drawable.aries
            "Телец" -> R.drawable.taurus
            "Близнецы" -> R.drawable.gemini
            "Рак" -> R.drawable.cancer
            "Лев" -> R.drawable.leo
            "Дева" -> R.drawable.virgo
            "Весы" -> R.drawable.libra
            "Скорпион" -> R.drawable.scorpio
            "Стрелец" -> R.drawable.sagittarius
            "Козерог" -> R.drawable.capricorn
            "Водолей" -> R.drawable.aquarius
            "Рыбы" -> R.drawable.pisces

            else -> android.R.drawable.ic_menu_help
        }
    }
}