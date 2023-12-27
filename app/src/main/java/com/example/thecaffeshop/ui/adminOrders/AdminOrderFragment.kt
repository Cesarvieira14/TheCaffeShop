package com.example.thecaffeshop.ui.adminOrders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAdminOrderBinding
import com.example.thecaffeshop.model.OrderStatus
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userOrders.OrderFragment
import com.example.thecaffeshop.ui.userOrders.OrdersViewModel
import com.example.thecaffeshop.ui.userStore.ProductsListAdapter
import com.example.thecaffeshop.ui.userStore.StoreViewModel


class AdminOrderFragment : Fragment() {

    private lateinit var adminOrdersViewModel: AdminOrdersViewModel

    private var _binding: FragmentAdminOrderBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAdminOrderBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adminOrdersViewModel =
            ViewModelProvider(requireActivity()).get(AdminOrdersViewModel::class.java)

        adminOrdersViewModel.fetchOrdersList()

        val actionBar = (activity as AdminHomeActivity).supportActionBar
        actionBar?.show()
        actionBar?.setDisplayHomeAsUpEnabled(true)

        adminOrdersViewModel.order.observe(viewLifecycleOwner) { order ->
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
            )


            binding.orderProducts.adapter = adapter

            OrderFragment.formatOrderStatus(binding.orderStatus)


          binding.orderUpdateStatus.setOnClickListener {
             updateOrderStatus(order.orderId)

          }
        }
    }
   private fun updateOrderStatus(orderId: Int) {
       val newOrderStatus = when (binding.OrderStatusGroup.checkedRadioButtonId) {
         //  R.id.adminOrderPreparingRadioBtn -> OrderStatus.Preparing
           R.id.collectRadioButton -> OrderStatus.Collect
           else -> {
               Toast.makeText(requireContext(), "Please select a valid order status!", Toast.LENGTH_SHORT).show()
               return
           }
       }
       // Assuming adminOrdersViewModel has a function to update the order status
       adminOrdersViewModel.updateOrderStatus(orderId, newOrderStatus)

       // Optionally, you can observe the updated order status in the ViewModel
       adminOrdersViewModel.order.observe(viewLifecycleOwner) { updatedOrder ->
           // Handle the updated order status if needed
       }

       Toast.makeText(requireContext(), "Order status updated successfully!", Toast.LENGTH_SHORT).show()
       view?.findNavController()?.navigate(R.id.action_navigation_admin_manage_order_to_navigation_admin_orders)

   }
}