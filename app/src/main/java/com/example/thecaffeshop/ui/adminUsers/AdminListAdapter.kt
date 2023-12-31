package com.example.thecaffeshop.ui.adminUsers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.Order
import com.example.thecaffeshop.model.User
import com.google.android.material.chip.Chip

class AdminListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val usersList: List<User>,
) : ArrayAdapter<User>(context, R.layout.users_list_item, usersList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = layoutInflater.inflate(R.layout.users_list_item, null, true)

     val currentUser = usersList.get(position)


     val userName = rowView.findViewById(R.id.user_list_username) as TextView
     val userType =   rowView.findViewById(R.id.user_list_userType) as TextView
     val statusUser = rowView.findViewById(R.id.user_list_activity_chip) as Chip


     userName.text = currentUser.userName.toString()

        userType.text = if (currentUser.isAdmin) "Admin" else "User"

        statusUser.text = if (currentUser.isActive) "Active" else "Inactive"

        if (currentUser.isActive) {
            statusUser.setChipBackgroundColorResource(R.color.success)
        } else {
            statusUser.setChipBackgroundColorResource(R.color.error)
        }

     return rowView
 }
}