package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.dboj.cleanarchitecturecryptocurrencyapp.common.Constants
import org.dboj.cleanarchitecturecryptocurrencyapp.common.Resource
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model.CoinDetail
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.use_case.get_coin.GetCoinUseCase
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel()
{

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(
        coinId: String
    ) {
        getCoinUseCase(coinId = coinId).onEach { result ->
            result.manage()
        }.launchIn(viewModelScope)
    }

    private fun Resource.Success<CoinDetail>.manage() {
        _state.value = CoinDetailState(coin = this.data)
    }

    private fun Resource.Error<CoinDetail>.manage() {
        _state.value = CoinDetailState(error = this.message ?: "An unexpected error occurred")
    }

    private fun Resource.Loading<CoinDetail>.manage() {
        _state.value = CoinDetailState(isLoading = true)
    }

    private fun Resource<CoinDetail>.manage() {
        when(this) {
            is Resource.Success -> manage()
            is Resource.Error -> manage()
            is Resource.Loading -> manage()
        }
    }
}