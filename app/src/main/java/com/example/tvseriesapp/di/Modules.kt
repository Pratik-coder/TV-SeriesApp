package com.example.tvseriesapp.di

import com.example.tvseriesapp.adapter.*
import com.example.tvseriesapp.datamapping.TVLocalMapper
import com.example.tvseriesapp.datamapping.TVRemoteMapper
import com.example.tvseriesapp.localdb.LocalRepository
import com.example.tvseriesapp.localdb.LocalRepositoryImplementation
import com.example.tvseriesapp.packagerepository.TVRemoteRepository
import com.example.tvseriesapp.packagerepository.TVRemoteRepositoryImpl
import com.example.tvseriesapp.usecase.*
import com.example.tvseriesapp.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule= module {
    single{TVRemoteMapper()}
    single { TVLocalMapper()}
    factory<TVRemoteRepository>{TVRemoteRepositoryImpl(get())}
    factory <LocalRepository>{LocalRepositoryImplementation(androidContext(),get())}
    factory{TVListAdapter(androidContext())}
}

val onTheairModule= module {
    factory { OnTheAirUseCase(get())}
    viewModel { OnTheAirViewModel(get())}
}

val airTodayModule= module {
    factory {AiringTodayUseCase(get())}
    factory { AiringTodayLocalUseCase(get()) }
    viewModel { AiringTodayViewModel(get(),get())}
}

val searchTVModule= module {
    factory {SearchMovieUseCase(get())}
    viewModel { SearchViewModel(get()) }
}

val movieDetailModule= module {
    factory { MovieDetailUseCase(get())}
    factory {AddFavouriteTVUseCase(get())}
    factory { DeleteFavouriteUseCase(get()) }
    factory { GetFavouriteTVUseCase(get()) }
    factory { UpDateFavouriteUseCase(get()) }
    factory { CastUseCase(get())}
    factory { VideoListUseCase(get())}
    factory { SeasonListAdapter(androidContext())}
    factory { CreditListAdapter(androidContext())}
    factory { VideoListAdapter(androidContext()) }
    viewModel{MovieDetailViewModel(get(),get(),get(),get(),get(),get(),get())}
}

val seasonDetailModule= module {
    factory { SaesonDetailUseCase(get())}
    factory { EpisodeListAdapter(androidContext())}
    viewModel { SeasonDetailViewModel(get())}
}

val castDetailModule= module {
    factory { CastDetailUseCase(get())}
    factory { PersonImagesUseCase(get())}
    factory { AllCreditUseCase(get())}
    factory { PersonImageListAdapter(androidContext()) }
    factory { CombinedCreditListAdapter(androidContext()) }
    viewModel{CastDetailViewModel(get(),get(),get())}

}

val favouriteModule= module {
    factory { GetFavouriteMoviesUseCase(get())}
    viewModel { FavouriteViewModel(get())}
}