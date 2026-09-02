package com.nudge.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nudge.core.database.entity.RelationshipEntity
import com.nudge.core.model.RelationshipType

@Dao
interface RelationshipDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(relationship: RelationshipEntity)

    @Query("SELECT * FROM relationships WHERE sourceId = :entityId OR targetId = :entityId")
    suspend fun getForEntity(entityId: String): List<RelationshipEntity>

    @Query("SELECT * FROM relationships WHERE sourceId = :sourceId AND type = :type")
    suspend fun getBySourceAndType(sourceId: String, type: RelationshipType): List<RelationshipEntity>

    @Query("DELETE FROM relationships WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM relationships WHERE sourceId = :entityId OR targetId = :entityId")
    suspend fun deleteForEntity(entityId: String)
}
