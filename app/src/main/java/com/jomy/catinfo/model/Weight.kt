package com.jomy.catinfo.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

/**
 * A data class to represent Cat's weight in different breeds
 */
@Serializable
data class Weight (
    val imperial:String,
    val metric:String
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imperial)
        parcel.writeString(metric)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weight> {
        override fun createFromParcel(parcel: Parcel): Weight {
            return Weight(parcel)
        }

        override fun newArray(size: Int): Array<Weight?> {
            return arrayOfNulls(size)
        }
    }

}
