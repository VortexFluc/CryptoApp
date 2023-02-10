package com.vortexfluc.cryptoapp.di

import android.app.Application
import com.vortexfluc.cryptoapp.presentation.CoinDetailFragment
import com.vortexfluc.cryptoapp.presentation.CoinPriceListActivity
import com.vortexfluc.cryptoapp.presentation.CryptoApp
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(coinDetailFragment: CoinDetailFragment)
    fun inject(coinPriceListActivity: CoinPriceListActivity)

    fun inject(application: CryptoApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}