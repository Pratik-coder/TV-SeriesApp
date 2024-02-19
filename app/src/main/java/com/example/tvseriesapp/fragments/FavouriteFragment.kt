package com.example.tvseriesapp.fragments

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
import androidx.navigation.fragment.findNavController
import com.example.tvseriesapp.R
import com.example.tvseriesapp.adapter.TVListAdapter
import com.example.tvseriesapp.databinding.FragmentTvlistBinding
import com.example.tvseriesapp.extensions.setAnchorId
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.viewmodel.FavouriteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavouriteFragment : Fragment(R.layout.fragment_tvlist),TVListAdapter.OnItemClickListener {

    private val favouriteViewModel:FavouriteViewModel by sharedViewModel()
    private val tvListAdapter:TVListAdapter by inject()
    private  var favouriteBinding:FragmentTvlistBinding?=null
    private val binding get() = favouriteBinding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        favouriteBinding= FragmentTvlistBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpRecyclerView()
        setUpSwipeRefresh()
        favouriteViewModel.fetchFavouriteMovies()
        viewLifecycleOwner.lifecycleScope.launch {
            favouriteViewModel.favouriteTvList.collect{
                handleFavouriteListResponse(it)
            }
        }
    }

    override fun onItemClick(tvData: TVData, container: View) {
        val action=FavouriteFragmentDirections.navigateToTVDetails(tvData.id,tvData.poster_path)
        val options=ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
        Pair.create(container,container.transitionName))
        findNavController().navigate(action, ActivityNavigatorExtras(options))
    }

    private fun handleFavouriteListResponse(state:Resource<List<TVData>>){
        when(state.status){
            Resource.Status.LOADING->{
                binding.srlFragmentIvList.isRefreshing=true
            }

            Resource.Status.SUCCESS->{
                binding.srlFragmentIvList.isRefreshing=false
                loadFavouriteData(state.data)
            }

            Resource.Status.ERROR->{
                binding.srlFragmentIvList.isRefreshing=false
                Snackbar.make(binding.srlFragmentIvList,getString(R.string.error_message_pattern,state.message),Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty Data")
            }
        }
    }

    private fun loadFavouriteData(favouriteTvShows:List<TVData>?){
        favouriteTvShows?.let {
            tvListAdapter.clear()
            tvListAdapter.fillList(it)
        }
    }

    private fun setUpRecyclerView(){
        tvListAdapter.setOnTVClickListener(this)
        binding.rvFragmentTvList.adapter=tvListAdapter
    }

    private fun setUpSwipeRefresh(){
        binding.srlFragmentIvList.setOnRefreshListener {
            favouriteViewModel.fetchFavouriteMovies()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        favouriteBinding=null
        favouriteViewModel.disposable?.dispose()
    }
}