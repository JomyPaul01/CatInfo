package com.jomy.catinfo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A data class to represent the information to save favourite in server
 */
@Serializable
data class FavouriteModel(
    @SerialName(value = "image_id")
    val imageId:String,

    @SerialName(value ="sub_id")
    val subId:String
)
