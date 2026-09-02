package com.nudge.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nudge.core.database.entity.CaptureEntity
import com.nudge.core.model.ProcessingStatus
import com.nudge.core.model.SyncStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface CaptureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(capture: CaptureEntity)

    @Update
    suspend fun update(capture: CaptureEntity)

    @Delete
    suspend fun delete(capture: CaptureEntity)

    @Query("SELECT * FROM captures ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<CaptureEntity>>

    @Query("SELECT * FROM captures WHERE id = :id")
    suspend fun getById(id: String): CaptureEntity?

    @Query("SELECT * FROM captures WHERE processingStatus = :status ORDER BY createdAt ASC")
    suspend fun getByProcessingStatus(status: ProcessingStatus): List<CaptureEntity>

    @Query("SELECT * FROM captures WHERE syncStatus = :status ORDER BY createdAt ASC")
    suspend fun getBySyncStatus(status: SyncStatus): List<CaptureEntity>

    @Query("""
        UPDATE captures 
        SET processingStatus = :status 
        WHERE id = :id
    """)
    suspend fun updateProcessingStatus(id: String, status: ProcessingStatus)

    @Query("""
        UPDATE captures 
        SET syncStatus = :status 
        WHERE id = :id
    """)
    suspend fun updateSyncStatus(id: String, status: SyncStatus)

    @Query("SELECT COUNT(*) FROM captures")
    suspend fun count(): Int
}
