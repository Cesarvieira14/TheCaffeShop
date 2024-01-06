package com.example.thecaffeshop.model

import java.io.Serializable

data class Comment(
    val comId: Int,
    var comText: String,
    var comDate: String,
    val customer: User,
    val prodId: Int,
) : Serializable {
    override fun toString(): String {
        return "Comment(" +
                "comId='$comId', " +
                "comText='$comText', " +
                "comDate='$comDate', " +
                "customer='$customer', " +
                "prodId='$prodId'" +
                ")"
    }
}
