package com.example.thecaffeshop.ui.userProfile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thecaffeshop.MainActivity
import com.example.thecaffeshop.ui.account.LoginActivity
import com.example.thecaffeshop.databinding.FragmentProfileBinding
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.clearValues

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileViewModel.fetchUserData()

        val userEmail: TextView = binding.profileUserEmail
        profileViewModel.userEmail.observe(viewLifecycleOwner) {
            userEmail.text = it
        }

        val userName: TextView = binding.profileUserName
        profileViewModel.userName.observe(viewLifecycleOwner) {
            userName.text = it
        }

        val userFullName: TextView = binding.profileUserFullName
        profileViewModel.userFullName.observe(viewLifecycleOwner) {
            userFullName.text = it
        }

        val userPhone: TextView = binding.profileUserPhone
        profileViewModel.userPhone.observe(viewLifecycleOwner) {
            userPhone.text = it
        }

        val logoutBtn: Button = binding.btnLogout
        logoutBtn.setOnClickListener {
            handleLogoutButtonClick()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleLogoutButtonClick() {
        val userPrefs = Session.userPreference(this.requireActivity().applicationContext)
        userPrefs?.clearValues = {}

        val login = Intent(this.context, MainActivity::class.java)
        startActivity(login)
    }
}