package com.vortexfluc.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.vortexfluc.cryptoapp.data.database.AppDatabase
import com.vortexfluc.cryptoapp.data.mapper.CoinMapper
import com.vortexfluc.cryptoapp.data.network.ApiFactory
import com.vortexfluc.cryptoapp.data.workers.RefreshDataWorkerFactory
import com.vortexfluc.cryptoapp.di.DaggerApplicationComponent

class CryptoApp : Application(), Configuration.Provider {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    AppDatabase.getInstance(this).coinPriceInfoDao(),
                    CoinMapper(),
                    ApiFactory.apiService
                )
            ).build()
    }
}