package com.example.thecaffeshop.ui.userOrders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentOrdersBinding
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderStatus

class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderViewModel =
            ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)

        val orderFilterOptions: ArrayList<String> = arrayListOf("All")
        OrderStatus.values().forEach { orderFilterOptions.add(it.toString()) }

        val orderFilterAdapter = ArrayAdapter(
            requireActivity().applicationContext,
            R.layout.order_filter_item,
            orderFilterOptions
        )
        binding.ordersFilterSpinner.adapter = orderFilterAdapter
        binding.ordersFilterSpinner.setSelection(0)
        binding.ordersFilterSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    orderViewModel.filterOrders(orderFilterOptions.get(position))
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        orderViewModel.filteredOrders.observe(viewLifecycleOwner) { ordersList ->
            val adapter = OrdersListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                ordersList
            );

            binding.ordersListView.setOnItemClickListener { adapterView, _, position, _ ->
                val orderAtPosition = adapterView.getItemAtPosition(position) as Order
                orderViewModel.selectOrder(orderAtPosition)
                view?.findNavController()
                    ?.navigate(R.id.action_navigation_user_orders_to_orderFragment)
            }
            binding.ordersListView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}