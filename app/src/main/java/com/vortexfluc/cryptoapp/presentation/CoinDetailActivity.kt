package com.vortexfluc.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.vortexfluc.cryptoapp.databinding.ActivityCoinDetailBinding
import com.vortexfluc.cryptoapp.utils.convertTimestampToTime

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
        val fSym = intent.getStringExtra(EXTRA_FSYM)

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]

        fSym?.let {
            viewModel.getDetailInfo(fSym).observe(this) {
                with(binding) {
                    Picasso.get().load(it.getFullImageUrl()).into(ivCurrency)
                    tvCurrencyName.text = it.fromsymbol
                    tvConvertedCurrencyName.text = it.tosymbol
                    tvPrice.text = it.price?.toString() ?: "Error!"
                    tvHighDay.text = it.highday?.toString()
                    tvLowDay.text = it.lowday?.toString()
                    tvLastMarket.text = it.lastmarket
                    tvLastUpdate.text = convertTimestampToTime(it.lastupdate)
                }

            }
        }


    }
    companion object {
        private const val TAG = "CoinDetailActivity"
        private const val EXTRA_FSYM = "fSym"

        fun newIntent(context: Context, fSym: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FSYM, fSym)
            return intent
        }
    }
}