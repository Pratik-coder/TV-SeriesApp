

package com.example.tvseriesapp.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tvseriesapp.R
import com.example.tvseriesapp.adapter.CombinedCreditListAdapter
import com.example.tvseriesapp.adapter.PersonImageListAdapter
import com.example.tvseriesapp.base.BaseActivity
import com.example.tvseriesapp.databinding.ActivityCastDetailsBinding
import com.example.tvseriesapp.databinding.ActivityTvdetailsBinding
import com.example.tvseriesapp.extensions.dp
import com.example.tvseriesapp.extensions.gone
import com.example.tvseriesapp.extensions.load
import com.example.tvseriesapp.extensions.visible
import com.example.tvseriesapp.model.PersonCast
import com.example.tvseriesapp.model.PersonProfile
import com.example.tvseriesapp.model.RemoteCastDetailData
import com.example.tvseriesapp.retrofit.RetrofitClient
import com.example.tvseriesapp.status.Resource
import com.example.tvseriesapp.utils.ColorUtils.darken
import com.example.tvseriesapp.viewmodel.CastDetailViewModel
import com.example.tvseriesapp.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle
import java.util.*

class CastDetailsActivity : BaseActivity() {

    private val castDetailViewModel: CastDetailViewModel by viewModel()
    private lateinit var binding: ActivityCastDetailsBinding
    private lateinit var personImageListAdapter: PersonImageListAdapter
    private lateinit var combinedCreditListAdapter: CombinedCreditListAdapter
    private var id: Int = 0




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCastDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolBar()
        clearStatusBar()
        postponeEnterTransition()
        id = intent.getIntExtra("castId", 1)
        castDetailViewModel.getCastDetails(id)
        castDetailViewModel.getPersonImages(id)
        castDetailViewModel.getAllCredits(id)
        lifecycleScope.launch {
            castDetailViewModel.castDetailData.collect {
                handleCastDetailData(it)
            }
        }

        lifecycleScope.launch{
            castDetailViewModel.personImageList.collect{
                handlePersonImages(it)
            }
        }

        lifecycleScope.launch{
            castDetailViewModel.creditList.collect{
                handlePersonCredits(it)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleCastDetailData(state: Resource<RemoteCastDetailData>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.progressBar.visible()
            }

            Resource.Status.SUCCESS -> {
                binding.progressBar.gone()
                loadCastDetailData(state.data)
            }

            Resource.Status.ERROR -> {
                binding.progressBar.gone()
                Toast.makeText(
                    this,
                    getString(R.string.error_message_pattern, state.message),
                    Toast.LENGTH_SHORT
                ).show()
            }

            Resource.Status.EMPTY -> {
                Log.d("TAG", "Empty data state")
            }
        }
    }

    private fun handlePersonImages(state:Resource<List<PersonProfile>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.progressBar.visible()
            }

            Resource.Status.SUCCESS -> {
                binding.progressBar.gone()
                loadPersonImages(state.data)
            }

            Resource.Status.ERROR -> {
                binding.progressBar.gone()
                Toast.makeText(
                    this,
                    getString(R.string.error_message_pattern, state.message),
                    Toast.LENGTH_SHORT
                ).show()
            }

            Resource.Status.EMPTY -> {
                Log.d("TAG", "Empty data state")
            }
        }
    }

    private fun handlePersonCredits(state:Resource<List<PersonCast>>) {
        when (state.status) {
            Resource.Status.LOADING -> {
                binding.progressBar.visible()
            }

            Resource.Status.SUCCESS -> {
                binding.progressBar.gone()
                loadCredits(state.data)
            }

            Resource.Status.ERROR -> {
                binding.progressBar.gone()
                Toast.makeText(
                    this,
                    getString(R.string.error_message_pattern, state.message),
                    Toast.LENGTH_SHORT
                ).show()
            }

            Resource.Status.EMPTY -> {
                Log.d("TAG", "Empty data state")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun loadCastDetailData(castDetailData: RemoteCastDetailData?) {
        castDetailData?.let {
                binding.ivActivityTvCastdetails.transitionName=castDetailData.id.toString()
                binding.ivActivityTvCastdetails.load(
                url = RetrofitClient.PROFILE_PATH + castDetailData.profilePath,
                width = 160.dp,
                height = 160.dp
            ) { color ->
                window?.statusBarColor = color.darken
                binding.collapsingToolbar.setBackgroundColor(color)
                binding.collapsingToolbar.setContentScrimColor(color)
                startPostponedEnterTransition()
                }
                binding.collapsingToolbar.title = castDetailData.name
                binding.textViewKnownFor.text = castDetailData.knownForDepartment

                val castBiography=castDetailData.biography
                if(!castBiography.isNullOrEmpty()){
                    binding.textViewBiography.text=castBiography
                    binding.textViewBiographyTitle.visibility=View.VISIBLE
                }
                  else{
                binding.textViewBiographyTitle.visibility=View.GONE
                }

                val placeOfBirth = castDetailData.placeOfBirth
                if (!placeOfBirth.isNullOrEmpty()) {
                    binding.textViewPlaceOfBirth.text = "BirthPlace: ${placeOfBirth}"
                    binding.textViewPlaceOfBirth.visibility = View.VISIBLE
                } else {
                    binding.textViewPlaceOfBirth.visibility = View.GONE
                }

            val alsoKnownAs=castDetailData.alsoKnownAs.joinToString()
            if(!alsoKnownAs.isEmpty()){
                 binding.textViewAlsoKnownAs.text="Also Known As: ${alsoKnownAs}"
                 binding.textViewAlsoKnownAs.visibility=View.VISIBLE
            }
            else{
                binding.textViewAlsoKnownAs.visibility=View.GONE
            }

                val birthDate = castDetailData.birthday
                binding.textViewCastBirthDate.text = if (!birthDate.isNullOrEmpty()) {
                    try {
                        val parseDate = LocalDate.parse(birthDate)
                        "BirthDate: ${
                            parseDate.format(
                                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(
                                    Locale.getDefault()
                                )
                            )
                        }"
                    } catch (e: DateTimeParseException) {
                        Log.e("TAG", "Error parsing date :$birthDate", e)
                        getString(R.string.str_invalidDate)
                    }
                } else {
                    getString(R.string.str_nodata)
                }
            }
        }

    private fun loadPersonImages(imageList:List<PersonProfile>?){
        if (imageList.isNullOrEmpty()){
            binding.textViewPhoto.gone()
        }
        else{
            binding.textViewPhoto.visible()
            binding.recyclerViewPersonImages.layoutManager=LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
            personImageListAdapter= PersonImageListAdapter(this,imageList)
            binding.recyclerViewPersonImages.adapter=personImageListAdapter
            personImageListAdapter.setOnPersonImageClickListener(object:PersonImageListAdapter.OnItemClickListener{
                override fun onItemClick(personData: PersonProfile) {
                   val personProfile=RetrofitClient.PROFILE_PATH + personData.filePath
                    val intent=Intent(Intent.ACTION_VIEW, Uri.parse(personProfile))
                    startActivity(intent)
                }
            })
        }
    }

    private fun loadCredits(creditList:List<PersonCast>?){
        if (creditList.isNullOrEmpty()){
            binding.textViewCredits.gone()
        }
        else{
            binding.textViewCredits.visible()
            binding.recyclerViewCredits.layoutManager=LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
            combinedCreditListAdapter= CombinedCreditListAdapter(this,creditList)
            binding.recyclerViewCredits.adapter=combinedCreditListAdapter
        }
    }


   private fun setUpToolBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        castDetailViewModel.disposable?.dispose()
    }
}


