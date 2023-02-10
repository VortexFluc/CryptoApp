package com.vortexfluc.cryptoapp.di

import android.app.Application
import com.vortexfluc.cryptoapp.data.database.AppDatabase
import com.vortexfluc.cryptoapp.data.database.CoinInfoDao
import com.vortexfluc.cryptoapp.data.network.ApiFactory
import com.vortexfluc.cryptoapp.data.network.ApiService
import com.vortexfluc.cryptoapp.data.repository.CoinRepositoryImpl
import com.vortexfluc.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {
    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository
    companion object {

        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}