package com.jomy.catinfo.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

/**
 * A data class to represent the image information
 */
@Serializable
data class ImageData(
    val id:String,
    val width:Int,
    val height:Int,
    val url:String
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageData> {
        override fun createFromParcel(parcel: Parcel): ImageData {
            return ImageData(parcel)
        }

        override fun newArray(size: Int): Array<ImageData?> {
            return arrayOfNulls(size)
        }
    }

}