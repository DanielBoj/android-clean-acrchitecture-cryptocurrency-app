package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.repository_service

import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.Coin
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.CoinDetail

interface ICoinRepository {

    suspend fun getCoins(): Result<List<Coin>>

    suspend fun getCoinById(coinId: String): Result<CoinDetail>
}