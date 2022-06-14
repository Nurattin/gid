package com.travel.gid.data.di

import com.travel.gid.BuildConfig
import com.travel.gid.data.datasource.network.GidApi
import com.travel.gid.data.repository.DirectionDetailRepositoryImpl
import com.travel.gid.data.repository.HomeRepositoryImpl
import com.travel.gid.data.repository.TourByCategoriesRepository
import com.travel.gid.data.repository.TourDetailRepositoryImpl
import com.travel.gid.domain.repository.DirectionDetailRepository
import com.travel.gid.domain.repository.HomeRepository
import com.travel.gid.domain.repository.TourDetailRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindHomeRepository(repo: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindDirectionDetailRepository(repo: DirectionDetailRepositoryImpl): DirectionDetailRepository

    @Binds
    abstract fun bindTourDetailRepository(repo: TourDetailRepositoryImpl): TourDetailRepository

    @Binds
    abstract fun bindTourByCategoriesRepository(repo: TourByCategoriesRepository): com.travel.gid.domain.repository.TourByCategoriesRepository

    companion object {
        @Provides
        @ViewModelScoped
        fun provideRetrofit(): Retrofit {
            val json = Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }

            val okHttpBuilder = OkHttpClient().newBuilder()
            okHttpBuilder.addInterceptor {
                val request = it.request()
                val url = request.url.newBuilder()
//                    .addQueryParameter("key", "key")
                    .build()
                it.proceed(request.newBuilder().url(url).build())
            }

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpBuilder.build())
                .build()
        }

        @Provides
        @ViewModelScoped
        fun providePixabayApi(retrofit: Retrofit): GidApi {
            return retrofit.create(GidApi::class.java)
        }

    }
}