package com.nudge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nudge.core.database.entity.NudgeEntity
import com.nudge.core.model.NudgeStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface NudgeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nudge: NudgeEntity)

    @Update
    suspend fun update(nudge: NudgeEntity)

    @Query("SELECT * FROM nudges ORDER BY scheduledAt DESC")
    fun observeAll(): Flow<List<NudgeEntity>>

    @Query("SELECT * FROM nudges WHERE status = :status ORDER BY scheduledAt ASC")
    suspend fun getByStatus(status: NudgeStatus): List<NudgeEntity>

    @Query("SELECT * FROM nudges WHERE scheduledAt <= :nowMillis AND status = 'SCHEDULED'")
    suspend fun getDueNudges(nowMillis: Long): List<NudgeEntity>

    @Query("UPDATE nudges SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: NudgeStatus)
}
