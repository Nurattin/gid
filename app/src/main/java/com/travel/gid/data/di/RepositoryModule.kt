package com.travel.gid.data.di

import com.travel.gid.data.repository.*
import com.travel.gid.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped



@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun binGidRepository(repo: GidListListRepositoryImpl): GidListRepository

    @Binds
    abstract fun binTourListFilter(repo: TourListRepositoryImpl): TourListRepository

    @Binds
    abstract fun binPlaceById(repo: PlaceByIdRepositoryImpl): PlaceByIdRepository

    @Binds
    abstract fun binFilterRepository(repo: FilterRepositoryImpl): FilterRepository

    @Binds
    abstract fun bindHomeRepository(repo: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindDirectionDetailRepository(repo: DirectionDetailRepositoryImpl): DirectionDetailRepository

    @Binds
    abstract fun bindTourDetailRepository(repo: TourDetailRepositoryImpl): TourDetailRepository

    @Binds
    abstract fun bindDirectionListRepository(repo: DirectionListRepositoryImpl): DirectionListRepository

    @Binds
    abstract fun bindEventListRepository(repo: EventListRepositoryImpl): EventListRepository
}