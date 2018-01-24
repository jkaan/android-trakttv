package app.joey.trakttv

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.joey.trakttv.data.TrendingMovie
import kotlinx.android.synthetic.main.row_trending_movie.view.*

class TrendingMovieViewHolder(val v: View): RecyclerView.ViewHolder(v) {
    fun bindMovie(movie: TrendingMovie) {
        v.textView.text = movie.movie.title
    }
}

class TrendingMoviesAdapter(
    val movies: List<TrendingMovie>) : RecyclerView.Adapter<TrendingMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TrendingMovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val view = layoutInflater.inflate(R.layout.row_trending_movie, parent, false)
        return TrendingMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingMovieViewHolder?, position: Int) {
        val movie = movies[position]

        holder?.bindMovie(movie)
    }

    override fun getItemCount() = movies.size
}
