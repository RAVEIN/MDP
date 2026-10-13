package com.example.mdp

import kotlin.random.Random

object BugFactory {

    private var nextId = 1L

    fun create(type: BugType): Bug {

        return when (type) {

            BugType.NORMAL -> Bug(
                id = nextId++,
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                directionX = randomDirection(),
                directionY = randomDirection(),
                speed = 0.10f,
                size = 0.12f,
                type = type,
                points = 10,
                imageResource = R.drawable.bug_normal
            )

            BugType.FAST -> Bug(
                id = nextId++,
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                directionX = randomDirection(),
                directionY = randomDirection(),
                speed = 0.20f,
                size = 0.09f,
                type = type,
                points = 20,
                imageResource = R.drawable.bug_fast
            )

            BugType.RARE -> Bug(
                id = nextId++,
                x = Random.nextFloat(),
                y = Random.nextFloat(),
                directionX = randomDirection(),
                directionY = randomDirection(),
                speed = 0.14f,
                size = 0.07f,
                type = type,
                points = 50,
                imageResource = R.drawable.bug_rare
            )
        }
    }

    private fun randomDirection(): Float {
        return Random.nextFloat() * 2f - 1f
    }
}