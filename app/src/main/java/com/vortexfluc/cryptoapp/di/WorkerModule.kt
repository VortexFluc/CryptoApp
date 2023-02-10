package com.vortexfluc.cryptoapp.di

import com.vortexfluc.cryptoapp.data.workers.ChildWorkerFactory
import com.vortexfluc.cryptoapp.data.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataFactory(worker: RefreshDataWorker.Factory): ChildWorkerFactory
}