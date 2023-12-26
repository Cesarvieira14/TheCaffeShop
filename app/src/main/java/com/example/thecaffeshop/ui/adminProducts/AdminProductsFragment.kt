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

        val adminProductsViewModel =
            ViewModelProvider(requireActivity()).get(AdminProductsViewModel::class.java)

        adminProductsViewModel.products.observe(viewLifecycleOwner) { productsList ->
            val adapter = ProductsListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                productsList
            );

            binding.adminProductsListView.setOnItemClickListener() { adapterView, _, position, _ ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product
                adminProductsViewModel.selectProduct(productAtPosition)
                 view?.findNavController()
                    ?.navigate(R.id.action_navigation_admin_products_to_manage_products)
            }




            binding.adminProductsListView.adapter = adapter
        }

        binding.AddProductBtn.setOnClickListener {

            // Navigate to add Product when the button is clicked
            view.findNavController()
                .navigate(R.id.action_navigation_admin_products_to_addProductsFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}