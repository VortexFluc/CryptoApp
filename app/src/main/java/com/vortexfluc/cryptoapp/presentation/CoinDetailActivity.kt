package com.vortexfluc.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.vortexfluc.cryptoapp.databinding.ActivityCoinDetailBinding

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private lateinit var binding: ActivityCoinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FSYM)) {
            finish()
            return
        }
        val fSym = intent.getStringExtra(EXTRA_FSYM) ?: EMPTY_STRING

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        fSym.let {
            viewModel.getDetailInfo(fSym).observe(this) {
                with(binding) {
                    Picasso.get().load(it.imageUrl).into(ivCurrency)
                    tvCurrencyName.text = it.fromSymbol
                    tvConvertedCurrencyName.text = it.toSymbol
                    tvPrice.text = it.price?.toString() ?: "Error!"
                    tvHighDay.text = it.highDay?.toString()
                    tvLowDay.text = it.lowDay?.toString()
                    tvLastMarket.text = it.lastMarket
                    tvLastUpdate.text = it.lastUpdate
                }

            }
        }


    }
    companion object {
        private const val TAG = "CoinDetailActivity"
        private const val EXTRA_FSYM = "fSym"
        private const val EMPTY_STRING = ""

        fun newIntent(context: Context, fSym: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FSYM, fSym)
            return intent
        }
    }
}