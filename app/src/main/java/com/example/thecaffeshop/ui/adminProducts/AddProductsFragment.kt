package com.example.thecaffeshop.ui.adminProducts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAddProductsBinding
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper

class AddProductsFragment : Fragment() {

    private lateinit var adminProductsViewModel: AdminProductsViewModel
    private var _binding: FragmentAddProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adminProductsViewModel = ViewModelProvider(requireActivity()).get(AdminProductsViewModel::class.java)

        binding.productAddProduct.setOnClickListener {
            handleAddProductBtnClick()
        }
    }

    private fun handleAddProductBtnClick() {
        val updatedName = binding.editProductTitle.text.toString().takeIf { it.isNotBlank() } ?: ""
        val updatedDescription = binding.editProductDescription.text.toString().takeIf { it.isNotBlank() } ?: ""
        val updatedImage = binding.editProductImage.text.toString().takeIf { it.isNotBlank() } ?: ""
        val updatedPriceString = binding.editProductPrice.text.toString().takeIf { it.isNotBlank() } ?: "0.0"
        val updatedPrice = updatedPriceString.toDouble()
        val updatedAvailability = binding.editProductAvailability.isChecked


        val newProduct = Product(
            0,
            prodName = updatedName,
            prodDescription = updatedDescription,
            prodImage = updatedImage,
            prodPrice = updatedPrice,
            prodAvailable = updatedAvailability
        )

        val dbHelper = ProductDBHelper(requireContext())
        val success = dbHelper.addProduct(newProduct)

        if (success) {
            Toast.makeText(requireContext(), "Product created successfully", Toast.LENGTH_SHORT).show()
            view?.findNavController()
                ?.navigate(R.id.action_navigation_addProductsFragment_to_navigation_admin_products)
                adminProductsViewModel.updateProductsList()
        } else {
            Toast.makeText(requireContext(), "Failed to create product", Toast.LENGTH_SHORT).show()
            // Handle the failure, perhaps show an error message
        }
    }
}