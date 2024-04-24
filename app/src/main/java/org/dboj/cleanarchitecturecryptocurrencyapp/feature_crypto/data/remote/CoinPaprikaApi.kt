package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote

import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.dto.CoinDetailDto
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.dto.CoinDto
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.Coin
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCoins(): Result<List<CoinDto>>

    @GET("/v1/coins/{ coinId }")
    suspend fun getCoinById(@Path("coinId") coinId: String): Result<CoinDetailDto>
}