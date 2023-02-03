package com.vortexfluc.cryptoapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import com.vortexfluc.cryptoapp.utils.convertTimestampToTime
import com.vortexfluc.cryptoapp.data.network.ApiFactory.BASE_IMAGE_URL

@Entity(tableName = "full_price_list")
data class CoinInfoDbModel(
    @PrimaryKey val fromSymbol: String,
    val toSymbol: String?,
    val price: Double?,
    val lastUpdate: Int?,
    val highDay: Double?,
    val lowDay: Double?,
    val lastMarket: String?,
    val imageUrl: String?
)
