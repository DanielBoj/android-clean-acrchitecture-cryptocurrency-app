package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.use_case.get_coin

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.dboj.cleanarchitecturecryptocurrencyapp.common.Resource
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.repository.CoinRepositoryException
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.CoinDetail
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.repository_service.ICoinRepository
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: ICoinRepository
) {

    operator fun invoke(
        coinId: String
    ): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())

            val coinDetail: CoinDetail = repository.getCoinById(coinId = coinId).getOrElse { throw CoinRepositoryException(it.message ?: "An unknown error occurred") }

            emit(Resource.Success(coinDetail))
        } catch(e: CoinRepositoryException) {
            emit(Resource.Error(e.message ?: "An unknown error occurred"))
        }
    }
}