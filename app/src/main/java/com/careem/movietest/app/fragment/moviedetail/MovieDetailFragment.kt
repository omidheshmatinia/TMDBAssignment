package com.careem.movietest.app.fragment.moviedetail

import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.careem.movietest.app.R
import com.careem.movietest.app.base.MasterApplication
import com.careem.movietest.app.base.MasterFragment
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFContract.TextViewType.*
import com.careem.movietest.app.fragment.moviedetail.MovieDetailFContract.ViewType.*
import com.careem.movietest.app.interfaces.SnackbarActionListener
import com.careem.movietest.app.model.MovieModel
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import javax.inject.Inject


class MovieDetailFragment : MasterFragment(), MovieDetailFContract.View {

    @Inject
    lateinit var presenter: MovieDetailFContract.Presenter
    private var scrollView :NestedScrollView? = null
    private var toolbar :Toolbar? = null
    private var posterImageView :ImageView? = null
    companion object {
        fun newInstance(movie:MovieModel):MovieDetailFragment{
            val fragment = MovieDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("movie",movie)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scrollView = nestedScrollView_movieDetailF
        toolbar = toolbar_movieDetailF
        posterImageView = imageView_movieDetailF_poster
        retainInstance = true
        MasterApplication.appComponent.inject(this)
        presenter.setMovieFromIntent(arguments?.getParcelable<MovieModel>("movie"))
        presenter.attachView(this)
    }

    override fun changeLayoutsBasedOnScroll() {
        val scrollPosition = scrollView?.scrollY ?: 0
        val alpha = Math.min(scrollPosition.toFloat() / resources.getDimensionPixelSize(R.dimen.movieDetailPosterFadeHeight), 1f)
        val alphaPoster = 1f-alpha
        toolbar?.alpha = alpha
        posterImageView?.alpha = alphaPoster
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (alpha == 1f)
                toolbar?.elevation = 2f
            else
                toolbar?.elevation = 0f
        }
    }

    override fun setupScrollAndToolbarStatus() {
        nestedScrollView_movieDetailF.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            presenter.scrollChanged()
        }
    }

    override fun showRetrySnack(msg:String, listener: SnackbarActionListener) {
        context?.run {
            showSnack(nestedScrollView_movieDetailF,msg, Snackbar.LENGTH_INDEFINITE,this.getString(R.string.try_again),{
                listener.actionClicked()
            },null)
        }
    }

    override fun setText(text: String, type: MovieDetailFContract.TextViewType) {
        when(type){
            Title -> textView_movieDetailF_title.text = text
            Description -> textView_movieDetailF_overview.text = text
            ReleaseDate ->  textView_movieDetailF_releaseDate.text = text
            VoteAverage -> textView_movieDetailF_averageRate.text = text
            Budget -> textView_movieDetailF_budget.text = text
            Revenue -> textView_movieDetailF_revenue.text = text
            Runtime -> textView_movieDetailF_runTime.text = text
        }
    }

    override fun hideView(type: MovieDetailFContract.ViewType) {
        when(type){
            DetailProgress -> progressBar_movieDetailF_detail.visibility = View.GONE
            CardViewBudget -> cardView_movieDetailF_budget.visibility = View.GONE
            CardViewRunTime -> cardView_movieDetailF_runtime.visibility = View.GONE
            CardViewRevenue -> cardView_movieDetailF_revenue.visibility = View.GONE
        }
    }

    override fun showView(type: MovieDetailFContract.ViewType) {
        when(type){
            DetailProgress -> progressBar_movieDetailF_detail.visibility = View.VISIBLE
            CardViewBudget -> cardView_movieDetailF_budget.visibility = View.VISIBLE
            CardViewRunTime -> cardView_movieDetailF_runtime.visibility = View.VISIBLE
            CardViewRevenue -> cardView_movieDetailF_revenue.visibility = View.VISIBLE
        }
    }

    override fun showPosterImage(url: String) {
        ImageLoader.getInstance()
                .displayImage(url,imageView_movieDetailF_poster)
    }

    override fun showBackDropImage(url: String) {
        ImageLoader.getInstance()
                .displayImage(url,imageView_movieDetailF_backdrop)
    }

    override fun onDetach() {
        presenter.destroy()
        super.onDetach()
    }
}