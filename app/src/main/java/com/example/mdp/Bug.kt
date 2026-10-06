package com.example.mdp

data class Bug(
    val id: Long,

    var x: Float,
    var y: Float,

    var directionX: Float,
    var directionY: Float,

    val speed: Float,
    val size: Float,

    val type: BugType,
    val points: Int,

    val imageResource: Int
)