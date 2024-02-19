package com.example.tvseriesapp.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseriesapp.R
import com.example.tvseriesapp.adapter.EpisodeListAdapter
import com.example.tvseriesapp.base.BaseActivity
import com.example.tvseriesapp.databinding.ActivitySeasonDetailsBinding
import com.example.tvseriesapp.extensions.dp
import com.example.tvseriesapp.extensions.gone
import com.example.tvseriesapp.extensions.load
import com.example.tvseriesapp.extensions.visible
import com.example.tvseriesapp.model.Episode
import com.example.tvseriesapp.model.SeasonDeatilData
import com.example.tvseriesapp.retrofit.RetrofitClient
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.utils.ColorUtils.darken
import com.example.tvseriesapp.viewmodel.SeasonDetailViewModel
import com.example.tvseriesapp.widget.PaginationScrollListener
import kotlinx.android.synthetic.main.fragment_search.view.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class SeasonDetails : BaseActivity() {
    private val seasonDetailViewModel:SeasonDetailViewModel by viewModel()
    private lateinit var binding:ActivitySeasonDetailsBinding
    private val episodeListAdapter:EpisodeListAdapter by inject()
    private  var seasonNumber:String?=null
    private  var id:String?=null




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySeasonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolBar()
        clearStatusBar()
        setUpRecyclerView()

         seasonNumber=intent.getStringExtra("season_number")
        id=intent.getStringExtra("id")
        seasonDetailViewModel.fetchSeasonDetail(id!!,seasonNumber!!)
        lifecycleScope.launch {
            seasonDetailViewModel.seasonData.collect{
                handleSeasonDetailDataResponse(it)
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)

    private fun handleSeasonDetailDataResponse(state:Resource<SeasonDeatilData>){
        when(state.status){
            Resource.Status.LOADING->{
                binding.progressBar.visible()
            }
            Resource.Status.SUCCESS->{
                binding.progressBar.gone()
                loadSeasonDetail(state.data)
            }

            Resource.Status.ERROR->{
                binding.progressBar.gone()
                Toast.makeText(this,getString(R.string.error_message_pattern,state.message),Toast.LENGTH_SHORT).show()
                finish()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty data state")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadSeasonDetail(seasonDeatilData: SeasonDeatilData?){
        seasonDeatilData?.let {
            binding.collapsingToolbar.title=seasonDeatilData.name
            binding.detailDescription.text=seasonDeatilData.overview
            postponeEnterTransition()
            binding.ivActivityTvSeasondetails.load(url= RetrofitClient.POSTER_BASE_URL + seasonDeatilData.poster_path, width = 160.dp, height = 160.dp){
                    color->
                window?.statusBarColor=color.darken
                binding.collapsingToolbar.setBackgroundColor(color)
                binding.collapsingToolbar.setContentScrimColor(color)
                startPostponedEnterTransition()
            }
            binding.seasonAirdate.text=if (seasonDeatilData.air_date.isNotEmpty())
               LocalDate.parse(seasonDeatilData.air_date).format(
                   DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                       .withLocale(Locale.getDefault())) else getString(R.string.str_nodata)
            loadEpisodeList(seasonDeatilData.episodes)
        }
    }

    private fun setUpRecyclerView(){
       binding.rvEpisodeList.adapter=episodeListAdapter
    }

    private fun loadEpisodeList(episodeList:List<Episode>?){
        episodeList?.let {
            episodeListAdapter.clear()
            episodeListAdapter.fillList(it)
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

    private fun setUpPosterImage(seasonDeatilData:SeasonDeatilData){
        postponeEnterTransition()
        binding.ivActivityTvSeasondetails.load(url= RetrofitClient.POSTER_BASE_URL + seasonDeatilData.poster_path, width = 160.dp, height = 160.dp){
                color->
            window?.statusBarColor=color.darken
            binding.collapsingToolbar.setBackgroundColor(color)
            binding.collapsingToolbar.setContentScrimColor(color)
            startPostponedEnterTransition()
        }
    }

}