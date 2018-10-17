package com.careem.movietest.app.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.careem.movietest.app.R
import com.careem.movietest.app.base.PublicConstant
import com.careem.movietest.app.interfaces.SimpleListClickListener
import com.careem.movietest.app.model.MovieModel
import com.nostra13.universalimageloader.core.ImageLoader


class MovieListAdapter(private var movies:List<MovieModel>,var listener: SimpleListClickListener<MovieModel>) :RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list,parent,false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movies[position]
        //set title
        holder.title.text = item.title
        //show image
        ImageLoader.getInstance()
                .displayImage(PublicConstant.ImagePath+item.posterPath,holder.poster)
        // set onClickListener
        holder.wrapper.setOnClickListener{
            listener.itemClicked(item,position)
        }

        if(position== (itemCount - 2))
            listener.lastItemReached()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wrapper : View = itemView
        val title : TextView = itemView.findViewById(R.id.textView_movieListItem_movieTitle)
        val poster : ImageView = itemView.findViewById(R.id.imageView_movieListItem_moviePoster)
    }
}