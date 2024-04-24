package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.dboj.cleanarchitecturecryptocurrencyapp.common.Resource
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.Coin
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.use_case.get_coins.GetCoinsUseCase
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
): ViewModel() {

    // Mutable
    private val _state = mutableStateOf(CoinsListState())
    // Inmutable
    val state: State<CoinsListState> = _state

    init { getCoins() }

    private fun getCoins() {
        getCoinsUseCase().onEach {result ->
            result.manage()
        }.launchIn(viewModelScope)
    }

    private fun Resource.Success<List<Coin>>.manage() {
        _state.value = CoinsListState(coins = this.data ?: emptyList())
    }

    private fun Resource.Error<List<Coin>>.manage() {
        _state.value = CoinsListState(error = this.message ?: "An unexpected error occurred")
    }

    private fun Resource.Loading<List<Coin>>.manage() {
        _state.value = CoinsListState(isLoading = true)
    }

    private fun Resource<List<Coin>>.manage() {
        when(this) {
            is Resource.Success -> manage()
            is Resource.Error -> manage()
            is Resource.Loading -> manage()
        }
    }
}