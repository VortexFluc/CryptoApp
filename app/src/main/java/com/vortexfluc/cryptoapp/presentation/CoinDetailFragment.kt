package com.vortexfluc.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.vortexfluc.cryptoapp.databinding.FragmentCoinDetailBinding
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: CoinViewModel
    private var _binding: FragmentCoinDetailBinding? = null
    val binding: FragmentCoinDetailBinding
    get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding == null")

    private val component by lazy {
        (requireActivity().application as CryptoApp).component
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fSym = getSymbol()

        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

        fSym.let {
            viewModel.getDetailInfo(fSym).observe(viewLifecycleOwner) {
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

    private fun getSymbol(): String {
        val fSym = arguments?.getString(ARG_FSYM) ?: EMPTY_STRING
        if (fSym == EMPTY_STRING) {
            throw RuntimeException("Need to specify fromSymbol!")
        }
        return fSym
    }
    companion object {
        private const val TAG = "CoinDetailFragment"
        private const val ARG_FSYM = "fSym"
        private const val EMPTY_STRING = ""

        fun newInstance(fSym: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_FSYM, fSym)
                }
            }
        }
    }
}