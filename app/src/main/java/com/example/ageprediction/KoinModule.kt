package com.example.ageprediction

import android.content.Context
import androidx.room.Room
import com.example.ageprediction.Consts.BASE_URL
import com.example.ageprediction.ui.favorites.childrenFragments.FavouriteItemViewModel
import com.example.ageprediction.ui.home.childrenFragments.SearchItemViewModel
import com.example.ageprediction.ui.repository.NamesRepository
import com.example.ageprediction.ui.repository.NamesRepositoryImpl
import com.example.ageprediction.ui.retrofit.AgifyApi
import com.example.ageprediction.ui.retrofit.RetrofitClient
import com.example.ageprediction.ui.roomDB.NamesDatabase
import com.example.ageprediction.ui.roomDB.entities.favItem.FavItemJsonMapper
import com.example.ageprediction.ui.roomDB.entities.searchItem.SearchItemJsonMapper
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.io.File

val koinModule = module {

    single {
        val retrofitClient: RetrofitClient = get()

        retrofitClient.retrofit(BASE_URL)
            .create(AgifyApi::class.java)
    }

    single { RetrofitClient(client = get()) }

    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .cache(Cache(File(androidContext().cacheDir, "okhttp_cache"), 10L*1024L*1024L))
            .build()
    }

    single<NamesRepository> {
        NamesRepositoryImpl(
            namesDatabase = get(),
            service = get(),
            searchItemJsonMapper = get(),
            favItemJsonMapper = get()
        )
    }

    single { SearchItemJsonMapper() }

    single { FavItemJsonMapper() }

    single {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NamesDatabase::class.java, "namesDB"
            )
                //.addMigrations(MigrationDB.MIGRATION_1_2) <-- use this when adding migrations
                .build()

        buildDatabase(androidContext())
    }

    viewModel { params ->
        SearchItemViewModel(
            currName = params.get()
        )
    }

    viewModel { FavouriteItemViewModel() }

}