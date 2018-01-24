package app.joey.trakttv

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import app.joey.trakttv.data.MovieService
import com.jakewharton.rxbinding2.support.v7.widget.scrollEvents
import dagger.android.support.DaggerFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_movies.view.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [MoviesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [MoviesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoviesFragment : DaggerFragment() {
    private var compositeDisposable = CompositeDisposable()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: TrendingMoviesAdapter
    @Inject lateinit var movieService: MovieService
    private var page = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieService
            .getTrendingMovies(page = 1, limit = 20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                adapter = TrendingMoviesAdapter(result.toMutableList())
                view.recyclerView.adapter = adapter
            }.addTo(compositeDisposable)

        view.recyclerView.scrollEvents().filter {
            val visibleItems = layoutManager.childCount
            val totalItems = layoutManager.itemCount
            val firstVisibleItemPos = layoutManager.findFirstCompletelyVisibleItemPosition()

            (visibleItems + firstVisibleItemPos) >= totalItems && firstVisibleItemPos >= 0
        }.subscribe {
            page += 1
            movieService
                .getTrendingMovies(page = page, limit = 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    adapter.addAll(result)
                }.addTo(compositeDisposable)
        }

        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayout.VERTICAL

        view.recyclerView.layoutManager = layoutManager
    }

    override fun onStop() {
        super.onStop()

        compositeDisposable.dispose()
    }

//    override fun onAttach(context: Context?) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoviesFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): MoviesFragment {
            val fragment = MoviesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
