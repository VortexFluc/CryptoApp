package com.vortexfluc.cryptoapp.presentation

import androidx.lifecycle.ViewModel
import com.vortexfluc.cryptoapp.domain.GetCoinInfoListUseCase
import com.vortexfluc.cryptoapp.domain.GetCoinInfoUseCase
import com.vortexfluc.cryptoapp.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor(
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
    ) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    init {
        loadDataUseCase()
    }


    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)


    companion object {
        private const val TAG = "CoinViewModel"
    }
}