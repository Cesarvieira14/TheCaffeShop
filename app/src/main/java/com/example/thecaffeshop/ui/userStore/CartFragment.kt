package com.example.thecaffeshop.ui.userStore

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentCartBinding
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrdersDBHelper
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userHome.HomeActivity

class CartFragment : Fragment() {
    private lateinit var storeViewModel: StoreViewModel

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storeViewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)

        val actionBar = (activity as HomeActivity).supportActionBar
        actionBar?.show()
        actionBar?.title = "Cart"

        val context = requireActivity().applicationContext;

        storeViewModel.cart.observe(viewLifecycleOwner) { productsList ->
            val adapter = CartListAdapter(
                context,
                layoutInflater,
                productsList
            );
            adapter.setHandleProductRemove {
                handleProductRemove(it)
            }

            // TODO: check if this is working
            binding.cartListView.setOnItemClickListener() { adapterView, _, position, id ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product
                storeViewModel.selectProduct(productAtPosition)
                view?.findNavController()?.navigate(R.id.action_cartFragment_to_productFragment)
            }
            binding.cartListView.adapter = adapter

            // Update total text view
            var totalPrice: Double = 0.0
            productsList.forEach {
                totalPrice += it.prodPrice
            }
            binding.cartTotalPrice.text = "Total: £${"%.2f".format(totalPrice)}"

            binding.cartSubmitOrderBtn.setOnClickListener {
                handleNewOrder()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleNewOrder() {
        val hasOrderBeenMade = storeViewModel.makeAnOrder()

        if (hasOrderBeenMade) {
            storeViewModel.clearCart()

            Toast.makeText(
                this.context,
                "Order made successfully!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handleProductRemove(product: Product) {
        storeViewModel.removeFromCart(product)

        Toast.makeText(
            this.context,
            "${product.prodName} has been removed",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}