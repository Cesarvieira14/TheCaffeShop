package com.example.thecaffeshop.ui.userStore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.thecaffeshop.R
import com.example.thecaffeshop.model.Comment
import kotlin.properties.Delegates

class CommentsListAdapter(
    private val context: Context,
    private val layoutInflater: LayoutInflater,
    private val commentsList: List<Comment>,
) : ArrayAdapter<Comment>(context, R.layout.comment_list_item, commentsList) {
    private var _currentUserId by Delegates.notNull<Int>()
    private lateinit var _handleCommentRemove : (comment: Comment) -> Unit

    fun setHandleCommentRemove(callback: (comment: Comment) -> Unit){
        _handleCommentRemove = callback
    }

    fun setCurrentUserId(userId: Int){
        _currentUserId = userId
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val rowView = layoutInflater.inflate(R.layout.comment_list_item, null, true)

        val currentComment = commentsList.get(position)

        val commentText = rowView.findViewById(R.id.comment_list_text) as TextView
        val commentDatetime = rowView.findViewById(R.id.comment_list_date) as TextView
        val commentAuthor = rowView.findViewById(R.id.comment_list_author) as TextView
        val deleteCommentButton = rowView.findViewById(R.id.comment_list_delete_btn) as Button

        commentText.text = currentComment.comText
        commentDatetime.text = currentComment.comDate
        commentAuthor.text = currentComment.customer.userName

        if (_currentUserId != currentComment.customer.id) {
            deleteCommentButton.visibility = View.INVISIBLE
        } else {
            deleteCommentButton.visibility = View.VISIBLE
            deleteCommentButton.setOnClickListener {
                _handleCommentRemove(currentComment)
            }
        }
        return rowView
    }
}