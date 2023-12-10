package com.example.thecaffeshop.ui.profile.managment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.thecaffeshop.R

class UserManagmentFragment : Fragment() {

    companion object {
        fun newInstance() = UserManagmentFragment()
    }

    private lateinit var viewModel: UserManagmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_managment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserManagmentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}