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

    @Query(
        """
    SELECT
        players.fullName AS playerName,
        scores.score AS score,
        scores.difficulty AS difficulty,
        scores.hits AS hits,
        scores.misses AS misses,
        scores.accuracy AS accuracy,
        scores.timestamp AS timestamp

    FROM scores

    INNER JOIN players
        ON players.id = scores.playerId
    WHERE scores.id = (
    SELECT s2.id
    FROM scores AS s2
    WHERE s2.playerId = scores.playerId
    ORDER BY
        s2.score DESC,
        s2.timestamp DESC
    LIMIT 1
    )
    ORDER BY scores.score DESC
    """
    )
    suspend fun getRecords():
            List<RecordItem>
}
