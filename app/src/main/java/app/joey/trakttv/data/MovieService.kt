package app.joey.trakttv.data

import io.reactivex.Single
import retrofit2.http.GET

interface MovieService {
  @GET("/movies/trending")
  fun getTrendingMovies(): Single<List<TrendingMovie>>
}
