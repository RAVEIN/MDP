package com.example.mdp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ScoreDao {

    @Insert
    suspend fun insertScore(
        score: ScoreEntity
    ): Long


    @Query(
        "SELECT * FROM scores " +
                "ORDER BY score DESC, timestamp DESC"
    )
    suspend fun getAllScores():
            List<ScoreEntity>
}