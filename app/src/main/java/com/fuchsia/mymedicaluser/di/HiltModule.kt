package com.fuchsia.mymedicaluser.di

import com.fuchsia.mymedicaluser.api.ApiBuilder
import com.fuchsia.mymedicaluser.repo.Repo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class HiltModule {

    @Provides
    @Singleton

    fun provideApiService(): ApiBuilder {
        return ApiBuilder

    }

    @Provides
    @Singleton
    fun apiService(apiService: ApiBuilder): Repo {
        return Repo(apiService)

    }


}