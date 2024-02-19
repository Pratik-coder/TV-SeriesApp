package com.example.tvseriesapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.tvseriesapp.R
import com.example.tvseriesapp.adapter.TVListAdapter
import com.example.tvseriesapp.databinding.FragmentTvlistBinding
import com.example.tvseriesapp.extensions.gone
import com.example.tvseriesapp.extensions.setAnchorId
import com.example.tvseriesapp.extensions.visible
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.retrofit.RetrofitClient
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.viewmodel.AiringTodayViewModel
import com.example.tvseriesapp.widget.PaginationScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AirToday : Fragment(R.layout.fragment_tvlist),TVListAdapter.OnItemClickListener {

    private val airingTodayViewModel: AiringTodayViewModel by sharedViewModel()
    private val tvListAdapter:TVListAdapter by inject()
    private var airTodayListBinding:FragmentTvlistBinding?=null
    private val binding get() = airTodayListBinding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        airTodayListBinding= FragmentTvlistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
        setUpSwipeRefresh()
        airingTodayViewModel.refreshAiringTV()
        viewLifecycleOwner.lifecycleScope.launch {
            airingTodayViewModel.airingTVShow.collect{
                handleAiringTVResponse(it)
            }
        }
        airingTodayViewModel.getAiringTvSeriesLocally()
        viewLifecycleOwner.lifecycleScope.launch {
            airingTodayViewModel.localAiringTvShow.collect{
                handleAiringTvResponseLocally(it)
            }
        }
    }

    override fun onItemClick(tvData: TVData, container: View) {
        val action=AirTodayDirections.navigateToTVDetails(tvData.id,tvData.poster_path)
        val options= ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
         Pair.create(container,container.transitionName))
        findNavController().navigate(action, ActivityNavigatorExtras(options))
    }

    private fun handleAiringTVResponse(state:Resource<List<TVData>>){
        when(state.status){
            Resource.Status.LOADING->{
              binding.srlFragmentIvList.isRefreshing=true
            }

            Resource.Status.SUCCESS->{
                binding.srlFragmentIvList.isRefreshing=false
                loadTVShows(state.data)

            }

            Resource.Status.ERROR->{
                binding.srlFragmentIvList.isRefreshing=false
                binding.pbFragmentTvList.gone()
                Snackbar.make(binding.srlFragmentIvList,getString(R.string.error_message_pattern,state.message),Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty data state")
            }
        }
    }

    private fun handleAiringTvResponseLocally(state: Resource<List<TVData>>){
        when(state.status){
            Resource.Status.LOADING->{
                binding.srlFragmentIvList.isRefreshing=true
            }

            Resource.Status.SUCCESS->{
                binding.srlFragmentIvList.isRefreshing=false
                loadTvShowsLocally(state.data)
            }

            Resource.Status.EMPTY->{
                binding.srlFragmentIvList.isRefreshing=false
                binding.pbFragmentTvList.gone()
                Snackbar.make(binding.srlFragmentIvList,getString(R.string.error_message_pattern,state.message),Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty Data State")
            }
            else -> {}
        }
    }

    private fun loadTVShows(tvShows:List<TVData>?){
        tvShows?.let {
            if (airingTodayViewModel.isFirstPage()){
                tvListAdapter.clear()
            }
            tvListAdapter.fillList(it)
        }
    }

    private fun loadTvShowsLocally(tvShows: List<TVData>?){
        tvShows?.let {
            tvListAdapter.clear()
            tvListAdapter.fillList(it)
        }
    }

    private fun setUpRecyclerView(){
        tvListAdapter.setOnTVClickListener(this)
        binding.rvFragmentTvList.adapter=tvListAdapter
        binding.rvFragmentTvList.addOnScrollListener(object:PaginationScrollListener(binding.rvFragmentTvList.linearLayoutManager){
            override fun isLastPage(): Boolean {
                return airingTodayViewModel.isLastPage()
            }

            override fun isLoading(): Boolean {
               val isLoading=binding.srlFragmentIvList.isRefreshing
                if (isLoading){
                    binding.pbFragmentTvList.visible()
                }
                else{
                    binding.pbFragmentTvList.gone()
                }
                return isLoading
            }

            override fun loadMoreItems() {
                   airingTodayViewModel.fetchNextAiringTV()
            }

        })
    }

    private fun setUpSwipeRefresh(){
        binding.srlFragmentIvList.setOnRefreshListener {
            airingTodayViewModel.refreshAiringTV()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        airTodayListBinding=null
        airingTodayViewModel.disposable?.dispose()
    }
}