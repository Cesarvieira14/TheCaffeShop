package com.example.thecaffeshop.ui.userStore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
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
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storeViewModel =
            ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)

        storeViewModel.products.observe(viewLifecycleOwner) { productsList ->
            val adapter = ProductsListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                productsList
            );

            binding.productsListView.setOnItemClickListener() { adapterView, _, position, _ ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product
                storeViewModel.selectProduct(productAtPosition)
                view?.findNavController()
                    ?.navigate(R.id.action_navigation_user_store_to_productFragment)
            }

            binding.productsListView.adapter = adapter
        }

        storeViewModel.cart.observe(viewLifecycleOwner) { productsList ->
            binding.goToCart.text = "Go to cart (${productsList.size})"
        }

        binding.goToCart.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_navigation_user_store_to_cartFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}