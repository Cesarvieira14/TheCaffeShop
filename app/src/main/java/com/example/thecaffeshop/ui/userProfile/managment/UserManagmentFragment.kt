package com.example.thecaffeshop.ui.userProfile.managment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thecaffeshop.databinding.FragmentUserManagmentBinding

class UserManagmentFragment : Fragment() {

    private var _binding: FragmentUserManagmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userManagementViewModel =
            ViewModelProvider(this).get(UserManagmentViewModel::class.java)

        _binding = FragmentUserManagmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}