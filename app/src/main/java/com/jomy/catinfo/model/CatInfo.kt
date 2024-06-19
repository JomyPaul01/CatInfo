package com.jomy.catinfo.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A data class to represent the cat information
 */
@Serializable
data class CatInfo(
    val id:String,
    val name:String?,
    val temperament:String?,
    val description:String? = null,
    val weight:Weight?,
    @SerialName(value = "life_span")
    val lifespan:String?,

    @SerialName(value = "image")
    val image:ImageData? = null,

    var isFavourite: Boolean = false

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Weight::class.java.classLoader),
        parcel.readString(),
        parcel.readParcelable(ImageData::class.java.classLoader),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(temperament)
        parcel.writeString(description)
        parcel.writeParcelable(weight, flags)
        parcel.writeString(lifespan)
        parcel.writeParcelable(image, flags)
        parcel.writeByte(if (isFavourite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CatInfo> {
        override fun createFromParcel(parcel: Parcel): CatInfo {
            return CatInfo(parcel)
        }

        override fun newArray(size: Int): Array<CatInfo?> {
            return arrayOfNulls(size)
        }
    }

}


