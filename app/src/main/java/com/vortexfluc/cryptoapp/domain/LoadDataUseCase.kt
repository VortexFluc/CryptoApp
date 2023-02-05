package com.vortexfluc.cryptoapp.domain

class LoadDataUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.loadData()
}