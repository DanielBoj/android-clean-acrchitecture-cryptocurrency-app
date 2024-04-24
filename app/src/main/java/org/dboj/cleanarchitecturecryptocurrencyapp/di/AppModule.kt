package org.dboj.cleanarchitecturecryptocurrencyapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.dboj.cleanarchitecturecryptocurrencyapp.common.Constants
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.CoinPaprikaApi
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.repository.CoinRepositoryImp
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.repository_service.ICoinRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinPaprikaAPi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.PAPRIKA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi): ICoinRepository = CoinRepositoryImp(api = api)
}