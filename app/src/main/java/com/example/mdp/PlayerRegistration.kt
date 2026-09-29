package com.example.mdp

data class PlayerRegistration(
    val fullName: String,
    val gender: String,
    val course: String,
    val difficulty: Int,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val zodiac: String
)