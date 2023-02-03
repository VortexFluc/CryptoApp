package com.vortexfluc.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vortexfluc.cryptoapp.data.database.AppDatabase
import com.vortexfluc.cryptoapp.data.mapper.CoinMapper
import com.vortexfluc.cryptoapp.data.network.ApiFactory
import com.vortexfluc.cryptoapp.domain.CoinInfo
import com.vortexfluc.cryptoapp.domain.CoinRepository
import kotlinx.coroutines.delay

class CoinRepositoryImpl(
    application: Application
): CoinRepository {
    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return Transformations.map(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
            mapper.mapDbModelToEntity(it)
        }
    }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fSyms)
                val coinInfoListDto = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoListDto.map {
                    mapper.mapDtoToDbModel(it)
                }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }
}