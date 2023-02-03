package com.vortexfluc.cryptoapp.data.mapper

import com.google.gson.Gson
import com.vortexfluc.cryptoapp.data.database.CoinInfoDbModel
import com.vortexfluc.cryptoapp.data.network.model.CoinInfoDto
import com.vortexfluc.cryptoapp.data.network.model.CoinInfoJsonContainerDto
import com.vortexfluc.cryptoapp.data.network.model.CoinNamesListDto
import com.vortexfluc.cryptoapp.domain.CoinInfo

class CoinMapper {

    fun mapDtoToDbModel(dto: CoinInfoDto) =
        CoinInfoDbModel(
            fromSymbol = dto.fromsymbol,
            toSymbol = dto.tosymbol,
            price = dto.price,
            lastUpdate = dto.lastupdate,
            highDay = dto.highday,
            lowDay = dto.lowday,
            lastMarket = dto.lastmarket,
            imageUrl = dto.imageurl
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
            lastUpdate = dbModel.lastUpdate,
            highDay = dbModel.highDay,
            lowDay = dbModel.lowDay,
            lastMarket = dbModel.lastMarket,
            imageUrl = dbModel.imageUrl
        )
    }
}