package com.nudge.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nudge.core.database.entity.ActionEntity
import com.nudge.core.model.ActionStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(action: ActionEntity)

    @Update
    suspend fun update(action: ActionEntity)

    @Delete
    suspend fun delete(action: ActionEntity)

    @Query("SELECT * FROM actions ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<ActionEntity>>

    @Query("SELECT * FROM actions WHERE intentId = :intentId")
    suspend fun getByIntentId(intentId: String): List<ActionEntity>

    @Query("SELECT * FROM actions WHERE status = :status ORDER BY scheduledAt ASC")
    fun observeByStatus(status: ActionStatus): Flow<List<ActionEntity>>

    @Query("UPDATE actions SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: ActionStatus)

    @Query("SELECT * FROM actions WHERE scheduledAt <= :nowMillis AND status = 'SCHEDULED'")
    suspend fun getDueActions(nowMillis: Long): List<ActionEntity>
}
