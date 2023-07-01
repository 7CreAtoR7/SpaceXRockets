package ru.ilya.spacexrockets.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import ru.ilya.spacexrockets.data.repository.SpaceXRepositoryImpl
import ru.ilya.spacexrockets.domain.repository.SpaceXRepository
import ru.ilya.spacexrockets.util.Constants

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindSpaceXRepository(impl: SpaceXRepositoryImpl): SpaceXRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideSpaceXApi(): SpaceXApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SpaceXApi::class.java)

        }
    }
}