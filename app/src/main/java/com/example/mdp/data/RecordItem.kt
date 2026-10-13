package com.example.mdp.data

data class RecordItem(
    val playerName: String,
    val score: Int,
    val difficulty: Int,
    val hits: Int,
    val misses: Int,
    val accuracy: Int,
    val timestamp: Long
)