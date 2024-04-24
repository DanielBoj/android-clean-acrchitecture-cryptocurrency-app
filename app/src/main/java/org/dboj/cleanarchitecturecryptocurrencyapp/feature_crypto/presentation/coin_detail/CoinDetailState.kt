package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.presentation.coin_detail

import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.CoinDetail

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)