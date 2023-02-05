package com.vortexfluc.cryptoapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vortexfluc.cryptoapp.R
import com.vortexfluc.cryptoapp.domain.CoinInfo

class CoinInfoAdapter(private val context: Context): RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coinInfo = coinInfoList[position]
        with(holder) {
            with(coinInfo) {
                val symbolsTemplate = context.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.getString(R.string.last_update_template)
                tvCurrencyName.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvCurrencyData.text = price.toString()
                tvCurrencyLastDate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivCurrency)

                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    override fun getItemCount() = coinInfoList.size

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }

    inner class CoinInfoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var ivCurrency: ImageView = itemView.findViewById(R.id.ivCurrency)
        var tvCurrencyName: TextView = itemView.findViewById(R.id.tvCurrencyName)
        var tvCurrencyData: TextView = itemView.findViewById(R.id.tvCurrencyData)
        var tvCurrencyLastDate: TextView = itemView.findViewById(R.id.tvCurrencyLastDate)
    }
}