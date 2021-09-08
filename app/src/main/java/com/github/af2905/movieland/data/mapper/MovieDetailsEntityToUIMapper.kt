package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.database.entity.GenreEntity
import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.database.entity.ProductionCompanyEntity
import com.github.af2905.movieland.data.database.entity.ProductionCountryEntity
import com.github.af2905.movieland.helper.extension.fiveStarRating
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.helper.mapper.ListMapper
import com.github.af2905.movieland.presentation.model.item.Genre
import com.github.af2905.movieland.presentation.model.item.MovieDetailsItem
import com.github.af2905.movieland.presentation.model.item.ProductionCompany
import com.github.af2905.movieland.presentation.model.item.ProductionCountry
import javax.inject.Inject

class MovieDetailsEntityToUIMapper @Inject constructor(
    private val genreMapper: GenreEntityToUIListMapper,
    private val productionCompanyMapper: ProductionCompanyEntityToUIListMapper,
    private val productionCountryMapper: ProductionCountryEntityToUIListMapper
) : IMapper<MovieDetailsEntity, MovieDetailsItem> {
    override fun map(input: MovieDetailsEntity): MovieDetailsItem =
        with(input) {
            MovieDetailsItem(
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
                voteAverageStar = voteAverage.fiveStarRating().toFloat(),
                voteCount = voteCount,
                backdropPath = backdropPath,
                posterPath = input.posterPath
            )
        }

    class ProductionCountryEntityToUIListMapper @Inject constructor(mapper: ProductionCountryEntityToUIMapper) :
        ListMapper<ProductionCountryEntity, ProductionCountry>(mapper)

    class ProductionCountryEntityToUIMapper @Inject constructor() :
        IMapper<ProductionCountryEntity, ProductionCountry> {
        override fun map(input: ProductionCountryEntity) = ProductionCountry(input.iso, input.name)
    }

    class ProductionCompanyEntityToUIListMapper @Inject constructor(mapper: ProductionCompanyEntityToUIMapper) :
        ListMapper<ProductionCompanyEntity, ProductionCompany>(mapper)

    class ProductionCompanyEntityToUIMapper @Inject constructor() :
        IMapper<ProductionCompanyEntity, ProductionCompany> {
        override fun map(input: ProductionCompanyEntity) =
            ProductionCompany(input.id, input.name, input.originCountry, input.logoPath)
    }

    class GenreEntityToUIListMapper @Inject constructor(mapper: GenreEntityToUIMapper) :
        ListMapper<GenreEntity, Genre>(mapper)

    class GenreEntityToUIMapper @Inject constructor() : IMapper<GenreEntity, Genre> {
        override fun map(input: GenreEntity) = Genre(input.id, input.name)
    }
}