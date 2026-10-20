package com.example.mdp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val fullName: String,
    val gender: String,
    val course: String,
    val difficulty: Int,

    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,

    val zodiac: String
)