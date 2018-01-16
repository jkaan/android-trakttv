package app.joey.trakttv.data

data class TrendingMovie(val watchers: Int, val movie: Movie)
data class Movie(val title: String, val year: Int)
