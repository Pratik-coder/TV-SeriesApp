

package com.example.tvseriesapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.TimeUtils
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.text.HtmlCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseriesapp.R
import com.example.tvseriesapp.adapter.CreditListAdapter
import com.example.tvseriesapp.adapter.SeasonListAdapter
import com.example.tvseriesapp.adapter.VideoListAdapter
import com.example.tvseriesapp.base.BaseActivity
import com.example.tvseriesapp.databinding.ActivityTvdetailsBinding
import com.example.tvseriesapp.extensions.*
import com.example.tvseriesapp.model.*
import com.example.tvseriesapp.retrofit.RetrofitClient
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.utils.ColorUtils.darken
import com.example.tvseriesapp.viewmodel.MovieDetailViewModel
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class TVDetailsActivity : BaseActivity() {

    private val movieDetailViewModel:MovieDetailViewModel by viewModel()
    private lateinit var creditListAdapter:CreditListAdapter
    private lateinit var videoListAdapter: VideoListAdapter
    private lateinit var binding:ActivityTvdetailsBinding
    private val args:TVDetailsActivityArgs by navArgs()
    private val seasonListAdapter:SeasonListAdapter by inject()






    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityTvdetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolBar()
        clearStatusBar()
        postponeEnterTransition()
        setUpRecyclerView()
        movieDetailViewModel.getTVShowDetail(args.id.toString())
        movieDetailViewModel.getCastList(args.id.toString())
        movieDetailViewModel.getVideoList(args.id.toString())

        lifecycleScope.launch{
            movieDetailViewModel.tvDetails.collect{
                handleSingleTVDetail(it)
            }
        }

        lifecycleScope.launch{
            movieDetailViewModel.castFlow.collect{
               handleCastData(it)
            }
        }

        lifecycleScope.launch{
            movieDetailViewModel.videoList.collect{
                handleVideosData(it)
            }
        }

        lifecycleScope.launch {
            movieDetailViewModel.favouriteTv.collect{
                handleFavouriteTVStatus(it)
            }
        }
    }




    override fun onResume() {
        super.onResume()
        movieDetailViewModel.getCastList(args.id.toString())
        movieDetailViewModel.getVideoList(args.id.toString())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        hideFab()
    }



    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleSingleTVDetail(state:Resource<TVDetailData>){
        when(state.status){
            Resource.Status.LOADING->{
                binding.progressBar.visible()
            }

            Resource.Status.SUCCESS->{
                binding.progressBar.gone()
                loadTVDetail(state.data)
            }

            Resource.Status.ERROR->{
                binding.progressBar.gone()
                Toast.makeText(this,"Error :${state.message}",Toast.LENGTH_SHORT).show()
                finish()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty data state")
            }
        }
    }

    private fun handleCastData(state:Resource<List<Cast>>){
        when(state.status){
            Resource.Status.LOADING->{
                binding.progressBar.visible()
            }

            Resource.Status.SUCCESS->{
                binding.progressBar.gone()
                handleCastList(state.data)
            }

            Resource.Status.ERROR->{
                binding.progressBar.gone()
                Toast.makeText(this,"Error:${state.message}",Toast.LENGTH_SHORT).show()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty data state")
            }
        }
    }

    private fun handleVideosData(state:Resource<List<Videos>>){
        when(state.status){
            Resource.Status.LOADING->{
                binding.progressBar.visible()
            }

            Resource.Status.SUCCESS->{
                binding.progressBar.gone()
                handleVideoList(state.data)
            }

            Resource.Status.ERROR->{
                binding.progressBar.gone()
                Toast.makeText(this,"Error :${state.message}",Toast.LENGTH_SHORT).show()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty data State")
            }
        }
    }


    private fun handleCastList(castList:List<Cast>?){
        if(castList.isNullOrEmpty()){
            binding.textViewCast.gone()
        }
        else{
            binding.textViewCast.visible()
            binding.rvTvCredit.layoutManager=LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
            creditListAdapter=CreditListAdapter(this,castList)
            binding.rvTvCredit.adapter=creditListAdapter
            creditListAdapter.setOnCastClickListener(object :CreditListAdapter.OnItemClickListener{
                override fun onItemClick(cast: Cast) {
                    val intent=Intent(this@TVDetailsActivity,CastDetailsActivity::class.java)
                    intent.putExtra("castId",cast.id)
                    startActivity(intent)
                }
            })
        }
    }



    private fun handleVideoList(videoList: List<Videos>?) {
        if (videoList.isNullOrEmpty()) {
            binding.textViewVideo.gone()
        } else {
            binding.textViewVideo.visible()
            binding.recyclerViewVideoList.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
            videoListAdapter = VideoListAdapter(this, videoList)
            binding.recyclerViewVideoList.adapter = videoListAdapter
            videoListAdapter.setOnVideoClickListener(object : VideoListAdapter.OnItemClickListener {
                override fun onItemClick(videos: Videos) {
                    val uri = RetrofitClient.YOUTUBE_WATCH_URL + videos.key
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    startActivity(intent)
                }
            })
        }

}

    private fun handleFavouriteTVStatus(state:Resource<Boolean>){
        when(state.status){
            Resource.Status.LOADING->{}
            Resource.Status.SUCCESS->{
                updateFavouriteButton(state.data)
            }
            Resource.Status.ERROR->{
                Toast.makeText(this,getString(R.string.error_message_pattern,state.message),Toast.LENGTH_SHORT).show()
            }
            Resource.Status.EMPTY->{
                Log.d("TAG","Empty Data State")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadTVDetail(tvDetail:TVDetailData?){
        tvDetail?.let {
            binding.ivActivityTvDetails.transitionName=args.id.toString()
            binding.ivActivityTvDetails.load(url=RetrofitClient.BASE_BACKDROP_URL + tvDetail.backdrop_path, width = 160.dp, height = 160.dp){
                    color->
                window?.statusBarColor=color.darken
                binding.collapsingToolbar.setBackgroundColor(color)
                binding.collapsingToolbar.setContentScrimColor(color)
                startPostponedEnterTransition()
            }
            binding.collapsingToolbar.title=tvDetail.original_name
            binding.detailDescription.text=tvDetail.overview
            binding.companyName.text=tvDetail.production_companies.firstOrNull()?.name.orNa()
            binding.runtime.text=if (tvDetail.episode_run_time.isNullOrEmpty())
                getString(R.string.str_nodata)
            else com.example.tvseriesapp.utils.TimeUtils.formatMinutes(this,tvDetail.episode_run_time[0])
            binding.year.text = if (tvDetail.first_air_date.isNotEmpty())
               LocalDate.parse(tvDetail.first_air_date).format(
                   DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                    .withLocale(Locale.getDefault())) else getString(R.string.str_noreleasedate)

            binding.website.text = HtmlCompat.fromHtml(
                getString(
                    R.string.visit_website_url_pattern,
                    tvDetail.homepage,
                    getString(R.string.visit_website)
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            binding.website.movementMethod=LinkMovementMethod.getInstance()
            binding.tvSeasonNumbers.text="Seasons: "+tvDetail.number_of_seasons
            binding.tvEpisodeNumbers.text="Episodes: "+tvDetail.number_of_episodes
            fillGenres(tvDetail.genres)
            binding.detailRating.text=if (tvDetail.vote_average>0) tvDetail.vote_average.toString() else getString(R.string.str_norating)
            binding.detailVotes.text=if (tvDetail.vote_count>0) tvDetail.vote_count.toString() else getString(R.string.str_nodata)
            binding.favoriteFab.setOnClickListener {
                movieDetailViewModel.toggleFavourite(tvDetail)
            }
         //   getSeasonList(tvDetail.seasons)
            movieDetailViewModel.getFavouriteMovie(tvDetail)
        }
    }

    private fun fillGenres(genres:List<Genres>){
        for (g in genres){
           val chip=Chip(this)
            chip.text=g.name
            binding.genresChipGroup.addView(chip)
        }
    }

    private fun setUpRecyclerView(){
        binding.rvFragmentTvseasonList.adapter=seasonListAdapter
        seasonListAdapter.setOnSeasonClickListener(object :SeasonListAdapter.OnItemClickListener{
            override fun onItemClick(seasons: Seasons) {
               val intent=Intent(this@TVDetailsActivity,SeasonDetails::class.java)
                intent.putExtra("season_number",seasons.season_number)
                intent.putExtra("id",args.id.toString())
                startActivity(intent)
            }
        })
    }

    private fun getSeasonList(seasons:List<Seasons>?){
       seasons?.let {
           seasonListAdapter.clear()
           seasonListAdapter.fillList(it)
       }
    }

 private fun updateFavouriteButton(data:Boolean?){
     data?.let {favourite->
        binding.favoriteFab.setImageResource(
            if (favourite)
                R.drawable.ic_baseline_star_rate_24
        else R.drawable.ic_baseline_star_outline_24
        )
     }
 }

    private fun setUpToolBar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun hideFab(){
        (binding.favoriteFab.layoutParams as CoordinatorLayout.LayoutParams).behavior=null
        binding.favoriteFab.requestLayout()
        binding.favoriteFab.gone()
    }


    override fun onDestroy() {
        super.onDestroy()
        movieDetailViewModel.disposable?.dispose()
    }
}

