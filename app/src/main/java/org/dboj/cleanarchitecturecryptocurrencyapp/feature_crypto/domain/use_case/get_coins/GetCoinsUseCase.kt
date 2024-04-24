package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.use_case.get_coins

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dboj.cleanarchitecturecryptocurrencyapp.common.Resource
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.repository.CoinRepositoryException
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.Coin
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.repository_service.ICoinRepository
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: ICoinRepository
) {

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())

            val coins: List<Coin> = repository.getCoins().getOrElse { throw CoinRepositoryException(it.message ?: "An unknown error occurred") }

            emit(Resource.Success(coins))
        } catch (e: CoinRepositoryException) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }
}