package com.example.thecaffeshop.ui.adminUsers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAdminUsersBinding
import com.example.thecaffeshop.model.User


class AdminUsersFragment : Fragment() {

    private var _binding: FragmentAdminUsersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adminUsersViewModel =
            ViewModelProvider(requireActivity()).get(AdminUsersViewModel::class.java)

        adminUsersViewModel.fetchUsersList()

        val usersFilterOptions: ArrayList<String> = arrayListOf("All")

        val filterSpinner: Spinner = view.findViewById(R.id.users_filter_spinner)

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            usersFilterOptions
        )
        filterSpinner.adapter = adapter

        adminUsersViewModel.users.observe(viewLifecycleOwner) { filteredUsersList ->
            Log.d("AdminUsersFragment", "Filtered Users Observer: $filteredUsersList")

            val adapter = AdminListAdapter(
                requireActivity().applicationContext,
                layoutInflater,
                filteredUsersList
            )
            binding.usersListView.adapter = adapter
            Log.d("AdminUsersFragment", "Adapter item count: ${adapter.count}")
        }

        binding.usersListView.setOnItemClickListener() { adapterView, _, position, _ ->
            val userAtPosition = adapterView.getItemAtPosition(position) as User
            adminUsersViewModel.selectUser(userAtPosition)
            view?.findNavController()
                ?.navigate(R.id.action_navigation_admin_users_to_navigation_admin_manage_user)
        }

    }
}