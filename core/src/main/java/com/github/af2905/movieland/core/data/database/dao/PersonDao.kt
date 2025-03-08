package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.Person
import com.github.af2905.movieland.core.data.database.entity.PersonType
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person WHERE personType = :personType ORDER BY popularity DESC")
    fun getPeopleByType(personType: PersonType): Flow<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPeople(people: List<Person>)

    @Query("DELETE FROM Person WHERE personType = :personType")
    suspend fun deletePeopleByType(personType: PersonType)

    @Query("SELECT MAX(timeStamp) FROM Person WHERE personType = :personType")
    suspend fun getLastUpdated(personType: PersonType): Long?

    @Transaction
    suspend fun replacePeople(personType: PersonType, people: List<Person>) {
        deletePeopleByType(personType)
        insertPeople(people)
    }
}