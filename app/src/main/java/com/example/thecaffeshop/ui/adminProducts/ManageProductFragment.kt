package com.example.thecaffeshop.ui.adminProducts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentManageProductBinding
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.ProductDBHelper


class ManageProductFragment : Fragment() {
    private lateinit var adminProductsViewModel: AdminProductsViewModel

    private var _binding: FragmentManageProductBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageProductBinding.inflate(inflater, container, false)
        return binding.root
    }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       adminProductsViewModel =
           ViewModelProvider(requireActivity()).get(AdminProductsViewModel::class.java)


  //TODO: fix error in the action bar
    //  val actionBar = (activity as HomeActivity).supportActionBar
    //  actionBar?.show()
    //  actionBar?.setDisplayHomeAsUpEnabled(true)

       adminProductsViewModel.product.observe(viewLifecycleOwner) { product ->
          // actionBar?.title = "Manage - ${product.prodName}"

           binding.editProductTitle.setText(product.prodName)
           binding.editProductDescription.setText(product.prodDescription)
           binding.editProductImage.setText(product.prodImage)
           binding.editProductPrice.hint = "£${"%.2f".format(product.prodPrice)}"
           binding.editProductAvailability.isChecked = product.prodAvailable == true



       binding.productEditProduct.setOnClickListener {
           handleEditProductBtnClick(product)
       }
           binding.productDeleteProduct.setOnClickListener{
               handleDeleteProductBtnClick(product)
           }

           binding.productCancelEditProduct.setOnClickListener {
       // Navigate back when cancel button is clicked
       view.findNavController().popBackStack()

       }
   }
   }
    fun handleEditProductBtnClick(product: Product) {
        val updatedName = binding.editProductTitle.text.toString().takeIf { it.isNotBlank() } ?: product.prodName
        val updatedDescription = binding.editProductDescription.text.toString().takeIf { it.isNotBlank() } ?: product.prodDescription
        val updatedImage = binding.editProductImage.text.toString().takeIf { it.isNotBlank() } ?: product.prodImage
        val updatedPriceString = binding.editProductPrice.text.toString().takeIf { it.isNotBlank() } ?: "${"%.2f".format(product.prodPrice)}"
        val updatedPrice = updatedPriceString.toDouble()
        val updatedAvailability = binding.editProductAvailability.isChecked

        val updatedProduct = Product(
        prodId = product.prodId,
        prodName = updatedName,
        prodDescription = updatedDescription,
        prodImage = updatedImage, // image unchanged
        prodPrice = updatedPrice,
        prodAvailable = updatedAvailability
    )

        val dbHelper = ProductDBHelper(requireContext())
        val success = dbHelper.editProduct(updatedProduct)

        if (success) {
            Toast.makeText(requireContext(), "Product updated successfully", Toast.LENGTH_SHORT).show()
            view?.findNavController()
                ?.navigate(R.id.action_manageProduct_fragment_to_navigation_admin_products)
                adminProductsViewModel.updateProductsList()
        } else {
            Toast.makeText(requireContext(), "Failed to update product", Toast.LENGTH_SHORT).show()
            // Handle the failure, perhaps show an error message
        }
    }
    fun handleDeleteProductBtnClick(product:Product){
        val dbHelper = ProductDBHelper(requireContext())
        val deletedSuccessfully = dbHelper.deleteProduct(product)


        if (deletedSuccessfully) {
            Toast.makeText(requireContext(), "Product deleted successfully", Toast.LENGTH_SHORT).show()
            view?.findNavController()
                ?.navigate(R.id.action_manageProduct_fragment_to_navigation_admin_products)
            adminProductsViewModel.updateProductsList()
        } else {
            Toast.makeText(requireContext(), "Failed to delete product", Toast.LENGTH_SHORT).show()
        }
    }
}