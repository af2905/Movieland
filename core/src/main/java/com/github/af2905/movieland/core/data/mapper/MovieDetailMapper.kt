package com.github.af2905.movieland.core.data.mapper

import com.github.af2905.movieland.core.common.mapper.ListMapperImpl
import com.github.af2905.movieland.core.common.mapper.Mapper
import com.github.af2905.movieland.core.common.model.item.MovieDetailItem
import com.github.af2905.movieland.core.data.database.entity.Genre
import com.github.af2905.movieland.core.data.database.entity.MovieDetail
import com.github.af2905.movieland.core.data.database.entity.ProductionCompany
import com.github.af2905.movieland.core.data.database.entity.ProductionCountry
import com.github.af2905.movieland.core.data.dto.movie.GenreDto
import com.github.af2905.movieland.core.data.dto.movie.MovieDetailsDto
import com.github.af2905.movieland.core.data.dto.movie.ProductionCompanyDto
import com.github.af2905.movieland.core.data.dto.movie.ProductionCountryDto
import com.github.af2905.movieland.util.extension.fiveStarRating
import javax.inject.Inject
import com.github.af2905.movieland.core.common.model.item.Genre as UiGenre
import com.github.af2905.movieland.core.common.model.item.ProductionCompany as UiProductionCompany
import com.github.af2905.movieland.core.common.model.item.ProductionCountry as UiProductionCountry

/**from dto to entity mappers*/
class MovieDetailDtoToMovieDetailMapper @Inject constructor(
    private val genreMapper: GenreDtoToGenreListMapper,
    private val productionCompanyMapper: ProductionCompanyDtoToProductionCompanyListMapper,
    private val productionCountryMapper: ProductionCountryDtoToProductionCountryListMapper
) : Mapper<MovieDetailsDto, MovieDetail> {
    override fun map(input: MovieDetailsDto): MovieDetail =
        with(input) {
            MovieDetail(
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
                voteCount = voteCount,
                backdropPath = backdropPath,
                posterPath = posterPath
            )
        }
}

class ProductionCountryDtoToProductionCountryListMapper @Inject constructor(
    mapper: ProductionCountryDtoToProductionCountryMapper
) : ListMapperImpl<ProductionCountryDto, ProductionCountry>(mapper)

class ProductionCountryDtoToProductionCountryMapper @Inject constructor() :
    Mapper<ProductionCountryDto, ProductionCountry> {
    override fun map(input: ProductionCountryDto) =
        with(input) { ProductionCountry(iso = iso, name = name) }
}

class ProductionCompanyDtoToProductionCompanyListMapper @Inject constructor(
    mapper: ProductionCompanyDtoToProductionCompanyMapper
) : ListMapperImpl<ProductionCompanyDto, ProductionCompany>(mapper)

class ProductionCompanyDtoToProductionCompanyMapper @Inject constructor() :
    Mapper<ProductionCompanyDto, ProductionCompany> {
    override fun map(input: ProductionCompanyDto) = with(input) {
        ProductionCompany(id = id, name = name, originCountry = originCountry, logoPath = logoPath)
    }
}

class GenreDtoToGenreListMapper @Inject constructor(
    mapper: GenreDtoToGenreMapper
) : ListMapperImpl<GenreDto, Genre>(mapper)

class GenreDtoToGenreMapper @Inject constructor() : Mapper<GenreDto, Genre> {
    override fun map(input: GenreDto) = with(input) { Genre(id = id, name = name) }
}

/**from entity to item mappers*/
class MovieDetailToMovieDetailItemMapper @Inject constructor(
    private val genreMapper: GenreToUiGenreListMapper,
    private val productionCompanyMapper: ProductionCompanyToUiProductionCompanyListMapper,
    private val productionCountryMapper: ProductionCountryToUiProductionCountryListMapper
) : Mapper<MovieDetail, MovieDetailItem> {
    override fun map(input: MovieDetail): MovieDetailItem =
        with(input) {
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
                voteAverageStar = voteAverage.fiveStarRating().toFloat(),
                voteCount = voteCount,
                backdropPath = backdropPath,
                posterPath = posterPath
            )
        }

    class ProductionCountryToUiProductionCountryListMapper @Inject constructor(
        mapper: ProductionCountryToUiProductionCountryMapper
    ) : ListMapperImpl<ProductionCountry, UiProductionCountry>(mapper)

    class ProductionCountryToUiProductionCountryMapper @Inject constructor() :
        Mapper<ProductionCountry, UiProductionCountry> {
        override fun map(input: ProductionCountry) =
            with(input) { UiProductionCountry(iso = iso, name = name) }
    }

    class ProductionCompanyToUiProductionCompanyListMapper @Inject constructor(
        mapper: ProductionCompanyToUiProductionCompanyMapper
    ) : ListMapperImpl<ProductionCompany, UiProductionCompany>(mapper)

    class ProductionCompanyToUiProductionCompanyMapper @Inject constructor() :
        Mapper<ProductionCompany, UiProductionCompany> {
        override fun map(input: ProductionCompany) = with(input) {
            UiProductionCompany(
                id = id,
                name = name,
                originCountry = originCountry,
                logoPath = logoPath
            )
        }
    }

    class GenreToUiGenreListMapper @Inject constructor(
        mapper: GenreToUiGenreMapper
    ) : ListMapperImpl<Genre, UiGenre>(mapper)

    class GenreToUiGenreMapper @Inject constructor() : Mapper<Genre, UiGenre> {
        override fun map(input: Genre) = with(input) { UiGenre(id = id, name = name) }
    }
}

/**from item to entity mappers*/
class MovieDetailItemToMovieDetailMapper @Inject constructor(
    private val genreMapper: UiGenreToGenreListMapper,
    private val productionCompanyMapper: UiProductionCompanyToProductionCompanyListMapper,
    private val productionCountryMapper: UiProductionCountryToProductionCountryListMapper
) : Mapper<MovieDetailItem, MovieDetail> {
    override fun map(input: MovieDetailItem): MovieDetail =
        with(input) {
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
                posterPath = posterPath
            )
        }

    class UiProductionCountryToProductionCountryListMapper @Inject constructor(
        mapper: UiProductionCountryToProductionCountryMapper
    ) : ListMapperImpl<UiProductionCountry, ProductionCountry>(mapper)

    class UiProductionCountryToProductionCountryMapper @Inject constructor() :
        Mapper<UiProductionCountry, ProductionCountry> {
        override fun map(input: UiProductionCountry) =
            with(input) { ProductionCountry(iso = iso, name = name) }
    }

    class UiProductionCompanyToProductionCompanyListMapper @Inject constructor(
        mapper: UiProductionCompanyToProductionCompanyMapper
    ) : ListMapperImpl<UiProductionCompany, ProductionCompany>(mapper)

    class UiProductionCompanyToProductionCompanyMapper @Inject constructor() :
        Mapper<UiProductionCompany, ProductionCompany> {
        override fun map(input: UiProductionCompany) = with(input) {
            ProductionCompany(
                id = id,
                name = name,
                originCountry = originCountry,
                logoPath = logoPath
            )
        }
    }

    class UiGenreToGenreListMapper @Inject constructor(
        mapper: UiGenreToGenreMapper
    ) : ListMapperImpl<UiGenre, Genre>(mapper)

    class UiGenreToGenreMapper @Inject constructor() : Mapper<UiGenre, Genre> {
        override fun map(input: UiGenre) = with(input) { Genre(id = id, name = name) }
    }
}