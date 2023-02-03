package com.vortexfluc.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vortexfluc.cryptoapp.R
import com.vortexfluc.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.vortexfluc.cryptoapp.data.network.model.CoinInfoDto

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var rvCoinPriceList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_price_list)
        initViews()
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfoDto) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromsymbol
                )
                startActivity(intent)
            }
        }
        rvCoinPriceList.adapter = adapter
        viewModel.priceList.observe(this) {
            adapter.coinInfoList = it
        }
    }

    private fun initViews() {
        rvCoinPriceList = findViewById(R.id.rvCoinPriceList)
    }
    companion object {
        private const val TAG = "CoinPriceInfo"
    }
}