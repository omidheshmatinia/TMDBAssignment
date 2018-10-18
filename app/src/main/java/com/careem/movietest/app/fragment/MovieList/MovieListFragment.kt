package com.careem.movietest.app.fragment.movielist

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.careem.movietest.app.R
import com.careem.movietest.app.adapter.MovieListAdapter
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.base.MasterFragment
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFragment
import com.careem.movietest.app.fragment.movielist.MovieListFContract.ProgressType.*
import com.careem.movietest.app.interfaces.SimpleListClickListener
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_movie_list.*
import javax.inject.Inject

class MovieListFragment : MasterFragment(), MovieListFContract.View {

    @Inject
    lateinit var presenter: MovieListFContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retainInstance = true
        MasterApplication.appComponent.inject(this)
        presenter.attachView(this)
    }

    override fun showProgress(type: MovieListFContract.ProgressType) {
        activity?.runOnUiThread {
            when (type) {
                CenterProgressBar -> progressBar_movieListF_center.visibility= View.VISIBLE
                BottomProgressbar -> progressBar_movieListF_bottom.visibility= View.VISIBLE
            }
        }
    }

    override fun hideProgress(type: MovieListFContract.ProgressType) {
        activity?.runOnUiThread{
            when(type){
                CenterProgressBar -> progressBar_movieListF_center.visibility= View.GONE
                BottomProgressbar -> progressBar_movieListF_bottom.visibility= View.GONE
            }
        }
    }

    override fun setupList(items: List<MovieModel>,columnSize:Int) {
        recyclerView_movieListF_movies.layoutManager = GridLayoutManager(context,columnSize)
        recyclerView_movieListF_movies.adapter = MovieListAdapter(items,object:SimpleListClickListener<MovieModel>{
            override fun itemClicked(item: MovieModel, position: Int) {
                presenter.movieListItemClicked(item)
            }

            override fun lastItemReached() {
                presenter.movieListReachEnd()
            }
        })

    }

    override fun showDetailFragment(movie: MovieModel) {
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.animator.fade_in,R.animator.fade_out,R.animator.fade_in,R.animator.fade_out)
        transaction.add(R.id.fragment_mainActivity, MovieDetailFragment.newInstance(movie))
        transaction.addToBackStack("detail")
        transaction.commit()
    }

    override fun showRetrySnack(msg:String, listener:SnackbarActionListener) {
        context?.run {
            showSnack(recyclerView_movieListF_movies,msg, Snackbar.LENGTH_INDEFINITE,this.getString(R.string.try_again),{
                listener.actionClicked()
            },null)
        }
    }

    override fun addNewItemsToList(beginIndex: Int, newItemsSize: Int) {
        recyclerView_movieListF_movies.adapter.notifyItemRangeInserted(beginIndex,newItemsSize)
    }

    override fun onDetach() {
        presenter.destroy()
        super.onDetach()
    }
}