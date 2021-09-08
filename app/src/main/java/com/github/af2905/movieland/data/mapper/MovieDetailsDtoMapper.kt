package com.github.af2905.movieland.data.mapper

import com.github.af2905.movieland.data.database.entity.GenreEntity
import com.github.af2905.movieland.data.database.entity.MovieDetailsEntity
import com.github.af2905.movieland.data.database.entity.ProductionCompanyEntity
import com.github.af2905.movieland.data.database.entity.ProductionCountryEntity
import com.github.af2905.movieland.data.dto.GenreDto
import com.github.af2905.movieland.data.dto.MovieDetailsDto
import com.github.af2905.movieland.data.dto.ProductionCompanyDto
import com.github.af2905.movieland.data.dto.ProductionCountryDto
import com.github.af2905.movieland.helper.mapper.IMapper
import com.github.af2905.movieland.helper.mapper.ListMapper
import javax.inject.Inject

class MovieDetailsDtoToEntityMapper @Inject constructor(
    private val genreMapper: GenreDtoToEntityListMapper,
    private val productionCompanyMapper: ProductionCompanyDtoToEntityListMapper,
    private val productionCountryMapper: ProductionCountryDtoToEntityListMapper
) : IMapper<MovieDetailsDto, MovieDetailsEntity> {
    override fun map(input: MovieDetailsDto): MovieDetailsEntity =
        with(input) {
            MovieDetailsEntity(
                id = id,
                adult = adult ?: true,
                budget = budget,
                genres = genres?.let { genreMapper.map(it) },
                homepage = homepage.orEmpty(),
                imdbId = imdbId.orEmpty(),
                originalLanguage = originalLanguage.orEmpty(),
                originalTitle = originalTitle.orEmpty(),
                overview = overview.orEmpty(),
                popularity = popularity,
                productionCompanies = productionCompanies?.let { productionCompanyMapper.map(it) },
                productionCountries = productionCountries?.let { productionCountryMapper.map(it) },
                releaseDate = releaseDate.orEmpty(),
                revenue = revenue,
                runtime = runtime,
                status = status.orEmpty(),
                tagline = tagline.orEmpty(),
                title = title.orEmpty(),
                video = video ?: false,
                voteAverage = voteAverage,
                voteCount = voteCount
            )
        }.also { it.backdropPath = input.backdropPath; it.posterPath = input.posterPath }
}

class ProductionCountryDtoToEntityListMapper @Inject constructor(mapper: ProductionCountryDtoToEntityMapper) :
    ListMapper<ProductionCountryDto, ProductionCountryEntity>(mapper)

class ProductionCountryDtoToEntityMapper @Inject constructor() :
    IMapper<ProductionCountryDto, ProductionCountryEntity> {
    override fun map(input: ProductionCountryDto) = ProductionCountryEntity(input.iso, input.name)
}

class ProductionCompanyDtoToEntityListMapper @Inject constructor(mapper: ProductionCompanyDtoToEntityMapper) :
    ListMapper<ProductionCompanyDto, ProductionCompanyEntity>(mapper)

class ProductionCompanyDtoToEntityMapper @Inject constructor() :
    IMapper<ProductionCompanyDto, ProductionCompanyEntity> {
    override fun map(input: ProductionCompanyDto) =
        ProductionCompanyEntity(input.id, input.name, input.originCountry)
            .also { it.logoPath = input.logoPath }
}

class GenreDtoToEntityListMapper @Inject constructor(mapper: GenreDtoToEntityMapper) :
    ListMapper<GenreDto, GenreEntity>(mapper)

class GenreDtoToEntityMapper @Inject constructor() : IMapper<GenreDto, GenreEntity> {
    override fun map(input: GenreDto) = GenreEntity(input.id, input.name)
}