package org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.domain.model

import com.google.gson.annotations.SerializedName
import org.dboj.cleanarchitecturecryptocurrencyapp.feature_crypto.data.remote.dto.*

data class CoinDetail(
    val coinId: String,
    val name: String,
    val description: String,
    val symbol: String,
    val rank: Int,
    val isActive: Boolean,
    val tags: List<String>,
    val team: List<TeamMember>,
)