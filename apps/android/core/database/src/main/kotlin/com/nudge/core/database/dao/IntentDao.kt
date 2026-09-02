package com.nudge.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nudge.core.database.entity.IntentEntity
import com.nudge.core.model.IntentStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface IntentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(intent: IntentEntity)

    @Update
    suspend fun update(intent: IntentEntity)

    @Delete
    suspend fun delete(intent: IntentEntity)

    @Query("SELECT * FROM intents ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<IntentEntity>>

    @Query("SELECT * FROM intents WHERE id = :id")
    suspend fun getById(id: String): IntentEntity?

    @Query("SELECT * FROM intents WHERE captureId = :captureId")
    suspend fun getByCaptureId(captureId: String): List<IntentEntity>

    @Query("SELECT * FROM intents WHERE status = :status ORDER BY createdAt DESC")
    fun observeByStatus(status: IntentStatus): Flow<List<IntentEntity>>

    @Query("UPDATE intents SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: IntentStatus)
}
