package com.medha.myapplication.di

import com.medha.myapplication.api.ApiService
import com.medha.myapplication.repositories.HomePageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped


@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideHomePageRepository(api: ApiService
    ): HomePageRepository {
        return HomePageRepository(api)
    }

}
