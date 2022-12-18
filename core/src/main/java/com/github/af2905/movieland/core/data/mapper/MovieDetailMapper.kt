package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.ProductionCountry
import com.github.af2905.movieland.core.data.dto.movie.GenreDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailDto
import com.github.af2905.movieland.core.data.dto.movie.ProductionCompanyDto
import com.github.af2905.movieland.core.data.dto.movie.ProductionCountryDto
import com.github.af2905.movieland.util.extension.fiveStarRating
import javax.inject.Inject
import com.github.af2905.movieland.core.common.model.item.Genre as UiGenre
import com.github.af2905.movieland.core.common.model.item.ProductionCompany as UiProductionCompany
import com.github.af2905.movieland.core.common.model.item.ProductionCountry as UiProductionCountry

class MovieDetailMapper @Inject constructor(
    private val genreMapper: GenreMapper,
    private val productionCompanyMapper: ProductionCompanyMapper,
    private val productionCountryMapper: ProductionCountryMapper,
    private val movieCreditsCastMapper: MovieCreditsCastMapper,
    private val movieMapper: MovieMapper
) {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: MovieDetailDto): MovieDetailItem = with(input) {
        MovieDetailItem(
            id = id,
            adult = adult,
            budget = budget,
            genres = genres?.let { genreMapper.map(it) },
            homepage = homepage,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
            productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            status = status,
            tagline = tagline,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            backdropPath = backdropPath,
            posterPath = posterPath,
            voteAverageStar = voteAverage?.fiveStarRating()?.toFloat()
        )
    }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: MovieDetail): MovieDetailItem = with(input) {
        MovieDetailItem(
            id = id,
            adult = adult,
            budget = budget,
            genres = genres?.let { genreMapper.map(it) },
            homepage = homepage,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
            productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            status = status,
            tagline = tagline,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteAverageStar = voteAverage?.fiveStarRating()?.toFloat(),
            voteCount = voteCount,
            backdropPath = backdropPath,
            posterPath = posterPath,
            liked = liked,
            movieCreditsCasts = movieCreditsCasts.let { movieCreditsCastMapper.map(it) },
            similarMovies = movieMapper.map(similarMovies)
        )
    }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: MovieDetailItem): MovieDetail = with(input) {
        MovieDetail(
            id = id,
            adult = adult,
            budget = budget,
            genres = genres?.let { genreMapper.map(it) },
            homepage = homepage,
            imdbId = imdbId,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
            productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            status = status,
            tagline = tagline,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount,
            backdropPath = backdropPath,
            posterPath = posterPath,
            liked = liked,
            movieCreditsCasts = movieCreditsCastMapper.map(movieCreditsCasts),
            similarMovies = movieMapper.map(similarMovies)
        )
    }
}

class GenreMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<GenreDto>): List<UiGenre> = input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<Genre>): List<UiGenre> = input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<UiGenre>): List<Genre> = input.map { item -> map(item) }

    private fun map(input: GenreDto): UiGenre = with(input) { UiGenre(id = id, name = name) }

    private fun map(input: Genre): UiGenre = with(input) { UiGenre(id = id, name = name) }

    private fun map(input: UiGenre): Genre = with(input) { Genre(id = id, name = name) }
}

class ProductionCompanyMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<ProductionCompanyDto>): List<UiProductionCompany> =
        input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<ProductionCompany>): List<UiProductionCompany> =
        input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<UiProductionCompany>): List<ProductionCompany> =
        input.map { item -> map(item) }

    private fun map(input: ProductionCompanyDto): UiProductionCompany = with(input) {
        UiProductionCompany(
            id = id,
            name = name,
            originCountry = originCountry,
            logoPath = logoPath
        )
    }

    private fun map(input: ProductionCompany): UiProductionCompany = with(input) {
        UiProductionCompany(
            id = id,
            name = name,
            originCountry = originCountry,
            logoPath = logoPath
        )
    }

    private fun map(input: UiProductionCompany): ProductionCompany = with(input) {
        ProductionCompany(
            id = id,
            name = name,
            originCountry = originCountry,
            logoPath = logoPath
        )
    }
}

class ProductionCountryMapper @Inject constructor() {
    @JvmName(DTO_TO_UI_ITEM_MAPPER)
    fun map(input: List<ProductionCountryDto>): List<UiProductionCountry> =
        input.map { dto -> map(dto) }

    @JvmName(ENTITY_TO_UI_ITEM_MAPPER)
    fun map(input: List<ProductionCountry>): List<UiProductionCountry> =
        input.map { entity -> map(entity) }

    @JvmName(UI_ITEM_TO_ENTITY_MAPPER)
    fun map(input: List<UiProductionCountry>): List<ProductionCountry> =
        input.map { item -> map(item) }

    private fun map(input: ProductionCountryDto): UiProductionCountry =
        with(input) { UiProductionCountry(iso = iso, name = name) }

    private fun map(input: ProductionCountry): UiProductionCountry =
        with(input) { UiProductionCountry(iso = iso, name = name) }

    private fun map(input: UiProductionCountry): ProductionCountry =
        with(input) { ProductionCountry(iso = iso, name = name) }
}