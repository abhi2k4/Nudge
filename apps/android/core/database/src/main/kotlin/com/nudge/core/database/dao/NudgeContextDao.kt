package com.nudge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nudge.core.database.entity.NudgeContextEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NudgeContextDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(context: NudgeContextEntity)

    @Update
    suspend fun update(context: NudgeContextEntity)

    @Query("SELECT * FROM contexts ORDER BY updatedAt DESC")
    fun observeAll(): Flow<List<NudgeContextEntity>>

    @Query("SELECT * FROM contexts WHERE id = :id")
    suspend fun getById(id: String): NudgeContextEntity?

    @Query("SELECT * FROM contexts ORDER BY updatedAt DESC LIMIT :limit")
    suspend fun getRecent(limit: Int = 20): List<NudgeContextEntity>

    @Query("DELETE FROM contexts WHERE id = :id")
    suspend fun deleteById(id: String)
}
