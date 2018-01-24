package app.joey.trakttv.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
  @GET("/movies/trending")
  fun getTrendingMovies(@Query("page") page: Int, @Query("limit") limit: Int): Single<List<TrendingMovie>>
}
