package com.vortexfluc.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vortexfluc.cryptoapp.R
import com.vortexfluc.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.vortexfluc.cryptoapp.domain.CoinInfo
import com.vortexfluc.cryptoapp.presentation.adapter.CoinInfoAdapter
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CoinViewModel

    private val binding: ActivityCoinPriceListBinding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as CryptoApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]
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