package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.CoinPaprikaApi
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.dto.toCoin
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.dto.toCoinDetail
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.Coin
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.CoinDetail
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.repository_service.ICoinRepository
import retrofit2.HttpException
import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(
    private val api: CoinPaprikaApi
): ICoinRepository {

    override suspend fun getCoins(): Result<List<Coin>> = withContext((Dispatchers.IO)) {
        return@withContext try {
            api.getCoins().let { res ->
                if (res.isSuccess) {
                    val coins = res.getOrNull()?.map { it.toCoin() } ?: throw CoinRepositoryException("Coins not found")

                    Result.success(coins)
                } else {
                    Result.failure(CoinRepositoryException(res.exceptionOrNull().toString()))
                }
            }
        } catch (e: HttpException) {
            Result.failure(CoinRepositoryException(e.message ?: "HttpException"))
        } catch (e: IOException) {
            Result.failure(CoinRepositoryException(e.message ?: "IOException"))
        }
    }

    override suspend fun getCoinById(coinId: String): Result<CoinDetail> = withContext(Dispatchers.IO) {
        return@withContext try {
            api.getCoinById(coinId).let { res ->
                if (res.isSuccess) {
                    val coinDetail = res.getOrNull()?.toCoinDetail() ?: throw CoinRepositoryException("Coin not found")

                    Result.success(coinDetail)
                } else {
                    Result.failure(CoinRepositoryException(res.exceptionOrNull().toString()))
                }
            }
        } catch (e: HttpException) {
            Result.failure(CoinRepositoryException(e.localizedMessage ?: "HttpException"))
        } catch (e: IOException) {
            Result.failure(CoinRepositoryException(e.localizedMessage ?: "Could not reach server. Check your internet connection."))
        }
    }
}

class CoinRepositoryException(message: String): Exception(message)