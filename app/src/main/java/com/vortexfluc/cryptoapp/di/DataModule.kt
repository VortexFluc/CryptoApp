package com.vortexfluc.cryptoapp.di

import android.app.Application
import com.vortexfluc.cryptoapp.data.database.AppDatabase
import com.vortexfluc.cryptoapp.data.database.CoinInfoDao
import com.vortexfluc.cryptoapp.data.repository.CoinRepositoryImpl
import com.vortexfluc.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
    companion object {

        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }
    }
}