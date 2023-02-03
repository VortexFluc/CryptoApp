package com.vortexfluc.cryptoapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vortexfluc.cryptoapp.data.repository.CoinRepositoryImpl
import com.vortexfluc.cryptoapp.domain.GetCoinInfoListUseCase
import com.vortexfluc.cryptoapp.domain.GetCoinInfoUseCase
import com.vortexfluc.cryptoapp.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    init {
        viewModelScope.launch {
            loadDataUseCase()
        }
    }


    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    companion object {
        private const val TAG = "CoinViewModel"
    }
}