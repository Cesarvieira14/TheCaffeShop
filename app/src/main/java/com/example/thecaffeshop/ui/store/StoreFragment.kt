package com.example.thecaffeshop.ui.store

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thecaffeshop.databinding.FragmentStoreBinding
import com.example.thecaffeshop.model.Product


class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val storeViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val productsSpinner: Spinner = binding.productsSpinner
        storeViewModel.products.observe(viewLifecycleOwner) { products ->
            val productsArray = ArrayList<String>()

            products.forEach { product ->
                productsArray.add(product.prodName)
            }

            val adapter: ArrayAdapter<String> = ArrayAdapter(
                this.requireActivity().applicationContext,
                R.layout.simple_spinner_dropdown_item,
                productsArray
            );
            productsSpinner.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}