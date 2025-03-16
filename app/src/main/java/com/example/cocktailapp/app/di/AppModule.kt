package com.example.cocktailapp.app.di

import com.example.cocktailapp.app.utils.Constants
import com.example.cocktailapp.data.remote.api.CocktailApiService
import com.example.cocktailapp.data.remote.repository.CocktailRepository
import com.example.cocktailapp.data.remote.repository.CocktailRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCocktailApiService(retrofit: Retrofit): CocktailApiService =
        retrofit.create(CocktailApiService::class.java)

    @Provides
    @Singleton
    fun provideCocktailRepository(apiService: CocktailApiService): CocktailRepository =
        CocktailRepositoryImpl(apiService)
}
