package com.github.af2905.movieland.data.repository.fake

import com.github.af2905.movieland.data.database.entity.MovieEntity
import com.github.af2905.movieland.data.database.entity.MoviesResponseEntity
import com.github.af2905.movieland.data.database.entity.ResponseWithMovies
import javax.inject.Inject

class FakeMoviesFactory @Inject constructor() {

    fun createFakeResponseWithMovies(): ResponseWithMovies {
        return ResponseWithMovies(
            moviesResponseEntity = createFakeMovieResponse(),
            movies = createFakeMovieList()
        )
    }

    private fun createFakeMovieResponse(): MoviesResponseEntity {
        return MoviesResponseEntity(
            dates = null,
            page = 1,
            totalPages = 1,
            totalResults = 2,
            movieType = "",
            timeStamp = null
        )
    }

    private fun createFakeMovieList(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                id = 527774,
                adult = false,
                genreIds = listOf(10751, 14, 16, 28, 12),
                originalLanguage = "en",
                originalTitle = "Raya and the Last Dragon",
                overview = "Давным-давно в волшебной стране Кумандре бок о бок с людьми жили драконы — создатели и хранители воды. Мирной жизни пришел конец, когда появились друуны. Порождения тьмы обращали людей и драконов в каменные изваяния, и только магия драконихи Сису смогла прогнать злых созданий и расколдовать людей, но на драконов не подействовала. Кумандра раскололась на государства Сердца, Клыка, Когтя, Хребта и Хвоста, а волшебный драконий камень хранился в стране Сердца, которой правил отец Райи. Он мечтал объединить земли, но из-за человеческой жадности и зависти артефакт раскололся. Правитель каждой страны заполучил по осколку, а друуны снова стали властвовать безраздельно...",
                popularity = 576.445,
                releaseDate = "2021-03-03",
                title = "Райя и последний дракон",
                video = false,
                voteAverage = 8.1,
                voteCount = 4076,
                responseMovieType = ""
            ).apply {
                backdropPath = "/hJuDvwzS0SPlsE6MNFOpznQltDZ.jpg"
                posterPath = "/tHane7cU8ACGU8uLqEDVEN3BuhQ.jpg"
            },
            MovieEntity(
                id = 602734,
                adult = false,
                genreIds = listOf(27, 53, 9648),
                originalLanguage = "en",
                originalTitle = "Spiral: From the Book of Saw",
                overview = "Импульсивный детектив полиции Нью-Йорка Зик Бэнкс всю жизнь пытается вырваться из тени своего отца, прославленного ветерана правоохранительных органов. Однажды Бэнксу и его новому напарнику поручают расследование серии жестоких убийств, которые странным образом напоминают преступления прошлых лет. Так они оказываются в эпицентре зловещей игры, и цена проигрыша в ней — человеческая жизнь.",
                popularity = 438.903,
                releaseDate = "2021-05-12",
                title = "Пила: Спираль",
                video = false,
                voteAverage = 6.4,
                voteCount = 708,
                responseMovieType = ""
            ).apply {
                backdropPath = "/g15PR8eQV9DehSWlagvdnJZqoRq.jpg"
                posterPath = "/9Xc26HQdeR3ySdRCN6dMt3952ha.jpg"
            }
        )
    }
}
