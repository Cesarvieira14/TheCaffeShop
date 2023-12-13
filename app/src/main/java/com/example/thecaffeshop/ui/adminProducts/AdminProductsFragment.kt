package com.example.thecaffeshop.ui.adminProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAdminProductsBinding
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userStore.ProductsListAdapter
import com.example.thecaffeshop.ui.userStore.StoreViewModel

class AdminProductsFragment : Fragment() {

    private var _binding: FragmentAdminProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminProductsBinding.inflate(inflater, container, false)
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

            binding.adminProductsListView.setOnItemClickListener() { adapterView, _, position, _ ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product
                storeViewModel.selectProduct(productAtPosition)
                 view?.findNavController()
           //         ?.navigate(R.id.action_navigation_user_store_to_productFragment)
            }

            binding.adminProductsListView.adapter = adapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}