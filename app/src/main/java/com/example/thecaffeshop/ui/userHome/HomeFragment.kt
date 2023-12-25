package com.example.thecaffeshop.ui.userHome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentHomeBinding
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.OrderStatus
import com.example.thecaffeshop.ui.userOrders.OrdersListAdapter
import com.example.thecaffeshop.ui.userOrders.OrdersViewModel
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.username

class HomeFragment : Fragment() {
    private lateinit var ordersViewModel: OrdersViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ordersViewModel = ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set home username to display
        val username = Session.userPreference(this.requireActivity().applicationContext).username
        binding.homeUsername.text = username

        binding.homeBrowseMenuBtn.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_navigation_user_home_to_navigation_user_store)
        }

        ordersViewModel.orders.observe(viewLifecycleOwner) { ordersList ->
            val filteredOrdersList =
                ordersList.filter { order ->
                    order.orderStatus == OrderStatus.Pending || order.orderStatus == OrderStatus.Processing
                }

            val adapter = OrdersListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                filteredOrdersList
            );

            binding.homeOrdersListView.setOnItemClickListener() { adapterView, _, position, _ ->
                val orderAtPosition = adapterView.getItemAtPosition(position) as Order
                ordersViewModel.selectOrder(orderAtPosition)
                view?.findNavController()
                    ?.navigate(R.id.action_navigation_user_home_to_orderFragment)
            }

            binding.homeOrdersListView.adapter = adapter
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}