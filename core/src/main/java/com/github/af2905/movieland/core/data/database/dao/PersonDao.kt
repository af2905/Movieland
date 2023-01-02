package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person")
    suspend fun get(): List<Person>?

    @Query("SELECT COUNT(*) FROM Person")
    suspend fun getCount(): Int?

    @Query("SELECT timeStamp FROM Person LIMIT 1")
    suspend fun getTimeStamp(): Long?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(person: Person)

    @Delete
    suspend fun delete(person: Person)
}