package com.example.thecaffeshop.ui.adminUsers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentAdminUserBinding
import com.example.thecaffeshop.databinding.FragmentManageProductBinding
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

           // binding.userEditUser.setOnClickListener {
           //     handleEditProductBtnClick(user)
           //
           // }

            binding.userEditUser.setOnClickListener {
                handleEditProductBtnClick(user)
            }

            binding.userCancelEditUser.setOnClickListener {
                // Navigate back when cancel button is clicked
                view.findNavController().popBackStack()
            }
        }
    }
    fun handleEditProductBtnClick(user: User) {
        val updateIsAdmin = binding.editUserAdmin.isChecked
        val updateIsActive = binding.editUserActive.isChecked


        val updatedUser = User(
            id = user.id,
            fullName = user.fullName,
            email = user.email,
            phoneNo = user.phoneNo,
            userName = user.userName,
            password = user.password,
            isActive = updateIsAdmin,
            isAdmin = updateIsActive
        )



}

}