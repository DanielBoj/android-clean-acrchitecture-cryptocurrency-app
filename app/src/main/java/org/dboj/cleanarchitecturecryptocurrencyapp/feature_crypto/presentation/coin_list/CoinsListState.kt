package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.presentation.coin_list

import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.Coin

data class CoinsListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)