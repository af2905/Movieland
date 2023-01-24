package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.PersonDetail

@Dao
interface PersonDetailDao {

    @Query("SELECT * FROM PersonDetail")
    suspend fun getAll(): List<PersonDetail>?

    @Query("SELECT * FROM PersonDetail WHERE id =:id")
    suspend fun getById(id: Int): PersonDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(personDetail: PersonDetail): Long?

    @Delete
    suspend fun delete(personDetail: PersonDetail): Int?
}