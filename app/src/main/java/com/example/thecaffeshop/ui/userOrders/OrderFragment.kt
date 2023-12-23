package com.example.thecaffeshop.ui.userOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentOrderBinding
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userHome.HomeActivity
import com.example.thecaffeshop.ui.userStore.ProductsListAdapter
import com.example.thecaffeshop.ui.userStore.StoreViewModel

class OrderFragment : Fragment() {
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var storeViewModel: StoreViewModel

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersViewModel = ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)
        storeViewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)

        val actionBar = (activity as HomeActivity).supportActionBar
        actionBar?.show()
        actionBar?.setDisplayHomeAsUpEnabled(true)

        ordersViewModel.order.observe(viewLifecycleOwner) { order ->
            actionBar?.title = "Order - ${order.orderId}"

            binding.orderOrderId.text = "Order ID: ${order.orderId}"
            binding.orderDatetime.text = "${order.orderDate} - ${order.orderTime}"
            binding.orderStatus.text = order.orderStatus
            binding.orderPaymentAmount.text = "Total amount: £${"%.2f".format(order.payment.paymentAmount)}"

            val adapter = ProductsListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                order.products
            );

            binding.orderProducts.setOnItemClickListener() { adapterView, _, position, _ ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product
                storeViewModel.selectProduct(productAtPosition)
                view?.findNavController()
                    ?.navigate(R.id.action_orderFragment_to_productFragment)
            }

            binding.orderProducts.adapter = adapter

            binding.orderPaymentBtn.setOnClickListener {
                Toast.makeText(requireContext(), "Payment successful!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}