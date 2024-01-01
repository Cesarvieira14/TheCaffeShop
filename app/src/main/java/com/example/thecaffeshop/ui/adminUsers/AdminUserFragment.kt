package com.example.thecaffeshop.ui.adminUsers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAdminUserBinding
import com.example.thecaffeshop.databinding.FragmentManageProductBinding
import com.example.thecaffeshop.model.AdminDBHelper
import com.example.thecaffeshop.model.CustomerDBHelper
import com.example.thecaffeshop.model.Product
import com.example.thecaffeshop.model.User
import com.example.thecaffeshop.ui.adminProducts.AdminProductsViewModel


class AdminUserFragment : Fragment() {
    private lateinit var adminUsersViewModel: AdminUsersViewModel

    private var _binding: FragmentAdminUserBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminUserBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adminUsersViewModel =
            ViewModelProvider(requireActivity()).get(AdminUsersViewModel::class.java)

        adminUsersViewModel.user.observe(viewLifecycleOwner) { user ->

            binding.userUsername.text = user.userName
            binding.editUserAdmin.isChecked = user.isAdmin == true
            binding.editUserActive.isChecked = user.isActive == true


            binding.userEditUser.setOnClickListener {
                handleEditUserBtnClick(user)
            }

            binding.userCancelEditUser.setOnClickListener {
                view.findNavController().popBackStack()
            }
            binding.userDeleteUser.setOnClickListener {
                handleDeleteUserBtnClick(user)
            }
        }
    }
    fun handleEditUserBtnClick(user: User) {
        val updateIsAdmin = binding.editUserAdmin.isChecked
        val updateIsActive = binding.editUserActive.isChecked
        Log.d("Debug", "Switch state before processing: $updateIsActive")
        Log.d("Debug", "Initial isAdmin state: ${user.isAdmin}")

        val updatedUser = User(
            id = user.id,
            fullName = user.fullName,
            email = user.email,
            phoneNo = user.phoneNo,
            userName = user.userName,
            password = user.password,
            isActive = updateIsActive,
            isAdmin = updateIsAdmin
        )

        if (updateIsAdmin != user.isAdmin) {
            // If the role is changed
            if (user.isAdmin) {
                // Admin to customer
                val adminDBHelper = AdminDBHelper(requireContext())
                adminDBHelper.removeAdmin(user.id)

                val customerDBHelper = CustomerDBHelper(requireContext())
                customerDBHelper.addCustomer(updatedUser)
            } else {
                // Customer to admin
                val customerDBHelper = CustomerDBHelper(requireContext())
                customerDBHelper.removeCustomer(user.id)

                val adminDBHelper = AdminDBHelper(requireContext())
                adminDBHelper.addAdmin(updatedUser)
            }
        } else {
            // when role is not changed, just update isActive
            if (user.isAdmin) {
                // Update admin
                val adminDBHelper = AdminDBHelper(requireContext())
                adminDBHelper.updateAdmin(updatedUser)
            } else {
                // Update customer
                val customerDBHelper = CustomerDBHelper(requireContext())
                customerDBHelper.updateCustomer(updatedUser)
            }
        }

        Log.d("Debug", "Final isActive state: $updateIsActive")
        view?.findNavController()?.popBackStack()
    }
    fun handleDeleteUserBtnClick(user: User) {

        if (user.isAdmin) {

            val adminDBHelper = AdminDBHelper(requireContext())
            adminDBHelper.removeAdmin(user.id)
        } else {
            val customerDBHelper = CustomerDBHelper(requireContext())
            customerDBHelper.removeCustomer(user.id)
        }
        view?.findNavController()?.popBackStack()
    }
}