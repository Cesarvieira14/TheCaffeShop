package com.example.thecaffeshop.ui.userOrders

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentOrderBinding
import com.example.thecaffeshop.model.OrderStatus
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userHome.HomeActivity
import com.example.thecaffeshop.ui.userStore.ProductsListAdapter
import com.example.thecaffeshop.ui.userStore.StoreViewModel

class OrderFragment : Fragment() {
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var storeViewModel: StoreViewModel

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun formatOrderStatus(statusText: TextView) {
            val enumOrderStatus = OrderStatus.values().find { it.name == statusText.text.toString() } as OrderStatus

            when (enumOrderStatus) {
                OrderStatus.Pending ->
                    statusText.setTextColor(Color.parseColor("#7C7C7C"))
                OrderStatus.Processing ->
                    statusText.setTextColor(Color.parseColor("#2196F3"))
                OrderStatus.Done ->
                    statusText.setTextColor(Color.parseColor("#4CAF50"))
                OrderStatus.Cancelled ->
                    statusText.setTextColor(Color.parseColor("#FF5722"))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersViewModel = ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)
        storeViewModel = ViewModelProvider(requireActivity()).get(StoreViewModel::class.java)

        ordersViewModel.fetchOrdersList()

        val actionBar = (activity as HomeActivity).supportActionBar
        actionBar?.show()
        actionBar?.setDisplayHomeAsUpEnabled(true)

        ordersViewModel.order.observe(viewLifecycleOwner) { order ->
            actionBar?.title = "Order - ${order.orderId}"

            binding.orderOrderId.text = "Order ID: ${order.orderId}"
            binding.orderDatetime.text = "${order.orderDate} - ${order.orderTime}"
            binding.orderStatus.text = order.orderStatus.toString()
            binding.orderPaymentAmount.text =
                "Total amount: £${"%.2f".format(order.payment.paymentAmount)}"

            val adapter = ProductsListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                order.products
            );

            binding.orderProducts.setOnItemClickListener() { adapterView, _, position, _ ->
                val productAtPosition = adapterView.getItemAtPosition(position) as Product
                storeViewModel.selectProduct(productAtPosition)
                view?.findNavController()?.navigate(R.id.action_orderFragment_to_productFragment)
            }

            binding.orderProducts.adapter = adapter

            formatOrderStatus(binding.orderStatus)

            if (order.orderStatus == OrderStatus.Pending) {
                binding.orderPaymentType.visibility = View.VISIBLE
                binding.orderPaymentType.clearCheck()
                binding.orderPaymentBtn.visibility = View.VISIBLE
                binding.orderCancelBtn.visibility = View.VISIBLE

                binding.orderPaymentBtn.setOnClickListener {
                    val checkedTypeId = binding.orderPaymentType.checkedRadioButtonId
                    if (checkedTypeId == -1) {
                        Toast.makeText(requireContext(), "Please select a payment method!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val checkedPaymentType = getView()?.findViewById<RadioButton>(checkedTypeId)

                    val hasPaymentBeenSuccessful = ordersViewModel.makePayment(checkedPaymentType?.text.toString())

                    if (hasPaymentBeenSuccessful) {
                        Toast.makeText(requireContext(), "Payment successful!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                }

                binding.orderCancelBtn.setOnClickListener {
                    val hasOrderBeenCancelled = ordersViewModel.cancelOrder()

                    if (hasOrderBeenCancelled) {
                        Toast.makeText(requireContext(), "Order cancelled!", Toast.LENGTH_SHORT).show()
                        view?.findNavController()?.navigate(R.id.navigation_user_orders)
                    } else {
                        Toast.makeText(requireContext(), "Something went wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                binding.orderPaymentType.visibility = View.INVISIBLE
                binding.orderPaymentBtn.visibility = View.INVISIBLE
                binding.orderCancelBtn.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}