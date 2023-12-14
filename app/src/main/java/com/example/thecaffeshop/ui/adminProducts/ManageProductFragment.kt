package com.example.thecaffeshop.ui.adminProducts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thecaffeshop.R
import com.example.thecaffeshop.databinding.FragmentManageProductBinding
import com.example.thecaffeshop.databinding.FragmentProductBinding


class ManageProductFragment : Fragment() {
    private lateinit var adminProductsViewModel: AdminProductsViewModel

    private var _binding: FragmentManageProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentManageProductBinding.inflate(inflater, container, false)
        return binding.root
    }


}