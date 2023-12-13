package com.example.thecaffeshop.ui.userStore

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thecaffeshop.databinding.FragmentProductBinding
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userHome.HomeActivity
import java.util.concurrent.Executors

class ProductFragment : Fragment() {
    private lateinit var storeViewModel: StoreViewModel

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeViewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)

        storeViewModel.product.observe(viewLifecycleOwner) { product ->
            val actionBar = (activity as HomeActivity).supportActionBar
            actionBar?.show()
            actionBar?.setHomeButtonEnabled(true)
            actionBar?.title = "Store - ${product.prodName}"

            binding.productTitle.text = product.prodName
            binding.productDescription.text = product.prodDescription
            binding.productPrice.text = "£${"%.2f".format(product.prodPrice)}"

            // Set product image
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap? = null
            executor.execute {
                try {
                    val `in` = java.net.URL(product.prodImage).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        binding.productImage.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            binding.productAddCart.setOnClickListener {
                handleAddToCartBtnClick(product)
            }
        }
    }

    fun handleAddToCartBtnClick(product: Product) {
        if (!product.prodAvailable) {
            Toast.makeText(requireContext(), "Product is not available", Toast.LENGTH_SHORT).show()
            return
        }

        storeViewModel.addToCart(product)

        Toast.makeText(
            requireContext(),
            "${product.prodName} added to your cart!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}