package com.example.thecaffeshop.ui.adminOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAdminOrdersBinding
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderStatus
import com.example.thecaffeshop.ui.userOrders.OrdersListAdapter
import com.example.thecaffeshop.ui.userOrders.OrdersViewModel

class AdminOrdersFragment : Fragment() {

    private var _binding: FragmentAdminOrdersBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adminOrdersViewModel =
            ViewModelProvider(this).get(AdminOrdersViewModel::class.java)

        _binding = FragmentAdminOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adminOrdersViewModel= ViewModelProvider(requireActivity()).get(AdminOrdersViewModel::class.java)

        val orderFilterOptions: ArrayList<String> = arrayListOf("All")
        OrderStatus.values().forEach { orderFilterOptions.add(it.toString()) }

        val orderFilterAdapter = ArrayAdapter(
            requireActivity().applicationContext,
            R.layout.order_filter_item,
            orderFilterOptions
        )
        binding.ordersFilterSpinner.adapter = orderFilterAdapter
        binding.ordersFilterSpinner.setSelection(0)
        binding.ordersFilterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long  ){
                adminOrdersViewModel.filterOrders(orderFilterOptions.get(position))
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        adminOrdersViewModel.filteredOrders.observe(viewLifecycleOwner) { ordersList ->
            val adapter = OrdersListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                ordersList
            );

           binding.ordersListView.setOnItemClickListener { adapterView, _, position, _ ->
               val orderAtPosition = adapterView.getItemAtPosition(position) as Order
               adminOrdersViewModel.selectOrder(orderAtPosition)
               view?.findNavController()
                   ?.navigate(R.id.action_navigation_admin_orders_to_navigation_admin_manage_order)
            }
            binding.ordersListView.adapter = adapter
        }
    }



   override fun onDestroyView() {
       super.onDestroyView()
       _binding = null
   }
}