package com.example.thecaffeshop.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thecaffeshop.LoginPage
import com.example.thecaffeshop.databinding.FragmentProfileBinding
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.clearValues

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
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

        val textView: TextView = binding.textNotifications
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
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
        val userPrefs = this.context?.let { Session.userPreference(it) }
        userPrefs?.clearValues = {}

        val login = Intent(this.context, LoginPage::class.java)
        startActivity(login)
    }
}