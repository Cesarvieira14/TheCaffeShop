package com.example.thecaffeshop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thecaffeshop.databinding.FragmentHomeBinding
import com.example.thecaffeshop.utils.Session
import com.example.thecaffeshop.utils.Session.username

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set home username to display
        val username = Session.userPreference(this.requireActivity().applicationContext).username
        binding.homeUsername.text = username

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}