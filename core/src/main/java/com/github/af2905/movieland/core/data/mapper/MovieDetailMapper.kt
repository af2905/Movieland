package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.GenreType
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.ProductionCountry
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import javax.inject.Inject

class MovieDetailMapper @Inject constructor() {
    fun map(dto: MovieDetailDto): MovieDetail {
        return MovieDetail(
            id = dto.id,
            title = dto.title,
            originalTitle = dto.originalTitle,
            originalLanguage = dto.originalLanguage,
            overview = dto.overview,
            tagline = dto.tagline,
            budget = dto.budget,
            revenue = dto.revenue,
            runtime = dto.runtime,
            releaseDate = dto.releaseDate,
            status = dto.status,
            adult = dto.adult,
            popularity = dto.popularity,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            homepage = dto.homepage,
            backdropPath = dto.backdropPath,
            posterPath = dto.posterPath,
            video = dto.video,
            genres = mapGenres(dto),
            productionCompanies = mapProductionCompanies(dto),
            productionCountries = mapProductionCountries(dto)
        )
    }

    private fun mapGenres(dto: MovieDetailDto): List<Genre> {
        return dto.genres?.map { genreDto ->
            Genre(
                id = genreDto.id,
                name = genreDto.name.orEmpty(),
                genreType = GenreType.MOVIE,
            )
        } ?: emptyList()
    }

    private fun mapProductionCompanies(dto: MovieDetailDto): List<ProductionCompany> {
        return dto.productionCompanies?.map { companyDto ->
            ProductionCompany(
                companyId = companyDto.id,
                movieId = dto.id,
                companyName = companyDto.name.orEmpty(),
                logoPath = companyDto.logoPath,
                originCountry = companyDto.originCountry.orEmpty()
            )
        } ?: emptyList()
    }

    private fun mapProductionCountries(dto: MovieDetailDto): List<ProductionCountry> {
        return dto.productionCountries?.map { countryDto ->
            ProductionCountry(
                countryId = countryDto.iso.hashCode(),
                movieId = dto.id,
                countryName = countryDto.name.orEmpty(),
                isoCode = countryDto.iso.orEmpty()
            )
        } ?: emptyList()
    }
}

