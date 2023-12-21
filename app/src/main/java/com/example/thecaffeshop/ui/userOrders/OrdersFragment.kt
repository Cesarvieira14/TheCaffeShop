package com.example.thecaffeshop.ui.userOrders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentOrdersBinding
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.ui.userStore.ProductsListAdapter
import com.example.thecaffeshop.ui.userStore.StoreViewModel

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

        val storeViewModel =
            ViewModelProvider(requireActivity()).get(OrdersViewModel::class.java)

        storeViewModel.orders.observe(viewLifecycleOwner) { ordersList ->
            val adapter = OrdersListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                ordersList
            );

            binding.ordersListView.setOnItemClickListener() { adapterView, _, position, _ ->
                val orderAtPosition = adapterView.getItemAtPosition(position) as Order
                storeViewModel.selectOrder(orderAtPosition)
                view?.findNavController()
                    ?.navigate(R.id.action_navigation_user_store_to_productFragment)
            }

            binding.ordersListView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}