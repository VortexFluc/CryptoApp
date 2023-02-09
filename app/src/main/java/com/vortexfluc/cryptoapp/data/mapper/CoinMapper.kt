package com.vortexfluc.cryptoapp.data.mapper

import com.google.gson.Gson
import com.vortexfluc.cryptoapp.data.database.CoinInfoDbModel
import com.vortexfluc.cryptoapp.data.network.model.CoinInfoDto
import com.vortexfluc.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.vortexfluc.cryptoapp.data.network.model.CoinNamesListDto
import com.vortexfluc.cryptoapp.domain.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class CoinMapper @Inject constructor() {
    companion object {
        const val BASE_IMAGE_URL = "https://cryptocompare.com/"
    }

    fun mapDtoToDbModel(dto: CoinInfoDto) =
        CoinInfoDbModel(
            fromSymbol = dto.fromsymbol,
            toSymbol = dto.tosymbol,
            price = dto.price,
            lastUpdate = dto.lastupdate,
            highDay = dto.highday,
            lowDay = dto.lowday,
            lastMarket = dto.lastmarket,
            imageUrl = BASE_IMAGE_URL + dto.imageurl
        )

    fun mapJsonContainerToListCoinInfo(jsonContainer: CoinInfoJsonContainerDto): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = jsonContainer.json ?: return result
        val coinKeySet = jsonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jsonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesList: CoinNamesListDto): String {
        return namesList.names?.map { it.coinName?.name }?.joinToString(",") ?: ""
    }

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel): CoinInfo {
        return CoinInfo(
            fromSymbol = dbModel.fromSymbol,
            toSymbol = dbModel.toSymbol,
            price = dbModel.price,
            lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }

    private fun convertTimestampToTime(timestamp: Int?): String {
        if (timestamp == null) return ""
        val longNum: Long = timestamp.toLong()
        val stamp = Timestamp(longNum * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"

        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}