package ru.ilya.spacexrockets.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ilya.spacexrockets.data.local.AppDatabase
import ru.ilya.spacexrockets.data.remote.SpaceXApi
import ru.ilya.spacexrockets.data.repository.SpaceXRepositoryImpl
import ru.ilya.spacexrockets.domain.repository.SpaceXRepository
import ru.ilya.spacexrockets.util.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindSpaceXRepository(impl: SpaceXRepositoryImpl): SpaceXRepository

    companion object {

        @Singleton
        @Provides
        fun provideAppDatabase(
            @ApplicationContext context: Context
        ): AppDatabase {
            return AppDatabase.getInstance(context)
        }

        @Provides
        @Singleton
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