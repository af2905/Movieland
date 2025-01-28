package com.github.af2905.movieland.core.data.database.dao

import androidx.room.*
import com.github.af2905.movieland.core.data.database.entity.CreditsCast
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.MovieDetailWithDetails
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.ProductionCountry

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCompanies(companies: List<ProductionCompany>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductionCountries(countries: List<ProductionCountry>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCast(cast: List<CreditsCast>)

    @Transaction
    @Query("SELECT * FROM MovieDetail WHERE id = :movieId")
    suspend fun getMovieWithDetails(movieId: Int): MovieDetailWithDetails
}