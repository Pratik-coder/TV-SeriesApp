package com.example.tvseriesapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.tvseriesapp.R
import com.example.tvseriesapp.adapter.TVListAdapter
import com.example.tvseriesapp.databinding.FragmentSearchBinding
import com.example.tvseriesapp.extensions.gone
import com.example.tvseriesapp.extensions.setAnchorId
import com.example.tvseriesapp.extensions.visible
import com.example.tvseriesapp.model.TVData
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.viewmodel.SearchViewModel
import com.example.tvseriesapp.widget.PaginationScrollListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class SearchFragment : Fragment(R.layout.fragment_search),TVListAdapter.OnItemClickListener {

    private val searchViewModel:SearchViewModel by sharedViewModel()
    private val tvListAdapter:TVListAdapter by inject()
    private var _binding:FragmentSearchBinding?=null
    private val binding
    get() = _binding!!




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpRecycler()
        swipeRefresh()
        searchViewModel.refreshSearchTV(binding.etSearchTv.text.toString())
        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchTVList.collect{
                handleSearchTVResponse(it)
            }
        }
        binding.etSearchTv.addTextChangedListener {
           it?.let {
               if (it.toString().isNotEmpty()){
                   searchViewModel.refreshSearchTV(binding.etSearchTv.text.toString())
               }
               else{
                   tvListAdapter.clear()
                   tvListAdapter.notifyDataSetChanged()
               }
           }
        }
    }


    private fun handleSearchTVResponse(state:Resource<List<TVData>>){
        when(state.status){

            Resource.Status.LOADING->{
                binding.srlFragmentSearchtvList.isRefreshing=true
            }

            Resource.Status.SUCCESS->{
                binding.srlFragmentSearchtvList.isRefreshing=false
                loadTvShows(state.data)
            }

            Resource.Status.ERROR->{
                binding.srlFragmentSearchtvList.isRefreshing=false
                Snackbar.make(binding.srlFragmentSearchtvList,getString(R.string.error_message_pattern,state.message),Snackbar.LENGTH_LONG)
                    .setAnchorId(R.id.bottom_navigation).show()
            }

            Resource.Status.EMPTY->{
                Log.d("TAG","Empty Data State")
            }
        }
    }



    private fun loadTvShows(tvShows:List<TVData>?){
        tvShows?.let {
            if (searchViewModel.isFirstPage()){
                tvListAdapter.clear()
            }
            tvListAdapter.fillList(it)
        }
    }

    private fun setUpRecycler(){
        tvListAdapter.setOnTVClickListener(this)
        binding.rvFragmentSearchtvList.adapter=tvListAdapter
        binding.rvFragmentSearchtvList.addOnScrollListener(object :PaginationScrollListener(binding.rvFragmentSearchtvList.linearLayoutManager){
            override fun isLastPage(): Boolean {
                return searchViewModel.isLastPage()
            }

            override fun isLoading(): Boolean {
               val isLoading=binding.srlFragmentSearchtvList.isRefreshing
                if (isLoading){
                    binding.pbFragmentSearchtvList.visible()
                }
                else{
                    binding.pbFragmentSearchtvList.gone()
                }
                return isLoading
            }

            override fun loadMoreItems() {
                searchViewModel.fetchNextSearchTV(binding.etSearchTv.text.toString())
            }
        })
    }

    private fun swipeRefresh(){
        binding.srlFragmentSearchtvList.setOnRefreshListener {
            searchViewModel.refreshSearchTV(binding.etSearchTv.text.toString())
        }
    }

    override fun onItemClick(tvData: TVData, container: View) {
        val action=SearchFragmentDirections.navigateToTVDetails(tvData.id,tvData.poster_path)
        val options=ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
        Pair.create(container,container.transitionName))
        findNavController().navigate(action, ActivityNavigatorExtras(options))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
        searchViewModel.disposable?.dispose()
    }

}