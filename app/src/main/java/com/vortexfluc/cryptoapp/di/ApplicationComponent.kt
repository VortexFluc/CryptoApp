package com.vortexfluc.cryptoapp.di

import android.app.Application
import com.vortexfluc.cryptoapp.presentation.CoinDetailFragment
import com.vortexfluc.cryptoapp.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(coinDetailFragment: CoinDetailFragment)
    fun inject(coinPriceListActivity: CoinPriceListActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}