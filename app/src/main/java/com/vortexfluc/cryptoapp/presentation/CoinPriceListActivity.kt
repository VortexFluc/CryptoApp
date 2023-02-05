package com.vortexfluc.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vortexfluc.cryptoapp.R
import com.vortexfluc.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.vortexfluc.cryptoapp.domain.CoinInfo
import com.vortexfluc.cryptoapp.presentation.adapter.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var rvCoinPriceList: RecyclerView

    private val binding: ActivityCoinPriceListBinding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                if (isPaneMode()) {
                    launchDetailActivity(coinPriceInfo.fromSymbol)
                } else {
                    launchDetailFragment(coinPriceInfo.fromSymbol)
                }
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null // Remove animation from RV.
        viewModel.coinInfoList.observe(this) {
            adapter.submitList(it)
        }
    }

    private fun isPaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fSym: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fSym
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fSym: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fSym))
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val TAG = "CoinPriceInfo"
    }
}