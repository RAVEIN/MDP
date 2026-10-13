package com.example.mdp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDao {

    @Insert
    suspend fun insertPlayer(
        player: PlayerEntity
    ): Long


    @Query(
        "SELECT * FROM players " +
                "ORDER BY fullName ASC"
    )
    suspend fun getAllPlayers():
            List<PlayerEntity>


    @Query(
        "SELECT * FROM players " +
                "WHERE id = :id LIMIT 1"
    )
    suspend fun getPlayerById(
        id: Long
    ): PlayerEntity?
}