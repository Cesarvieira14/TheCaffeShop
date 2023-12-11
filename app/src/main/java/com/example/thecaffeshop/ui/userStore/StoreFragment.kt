package com.example.thecaffeshop.ui.userStore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
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

        val context = this.requireActivity().applicationContext;

        val productsListView: ListView = binding.productsListView
        storeViewModel.products.observe(viewLifecycleOwner) { productsList ->
            val productsArray = ArrayList<String>()

            productsList.forEach { product ->
                productsArray.add(product.prodName)
            }

            val adapter = ProductsListAdapter(
                context,
                layoutInflater,
                productsList
            );

            binding.productsListView.setOnItemClickListener() { adapterView, _, position, id ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product

                val productActivity = Intent(context, ProductActivity::class.java)
                productActivity.putExtra(ProductActivity.PRODUCT_TAG, productAtPosition)
                startActivity(productActivity)
            }

            productsListView.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}