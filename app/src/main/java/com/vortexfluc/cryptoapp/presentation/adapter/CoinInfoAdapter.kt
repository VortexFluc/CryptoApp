package com.vortexfluc.cryptoapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vortexfluc.cryptoapp.R
import com.vortexfluc.cryptoapp.databinding.ItemCoinInfoBinding
import com.vortexfluc.cryptoapp.domain.CoinInfo

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinInfoDiffCallback) {

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coinInfo = getItem(position)
        with(holder.binding) {
            with(coinInfo) {
                val symbolsTemplate = context.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.getString(R.string.last_update_template)
                tvCurrencyName.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvCurrencyData.text = price.toString()
                tvCurrencyLastDate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivCurrency)

                root.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}