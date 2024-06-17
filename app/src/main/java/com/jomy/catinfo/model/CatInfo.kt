package com.jomy.catinfo.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatInfo(
    val id:String,
    val name:String?,
    val temperament:String?,
    val description:String? = null,
    val weight:Weight?,
    @SerialName(value = "life_span")
    val lifespan:String?,
    val adaptability:Int,

    @SerialName(value="affection_level")
    val affectionLevel:Int,

    @SerialName(value="child_friendly")
    val childFriendly:Int,

    @SerialName(value = "dog_friendly")
    val dogFriendly:Int,

    @SerialName(value ="shedding_level")
    val sheddingLevel:Int,

    @SerialName(value = "energy_level")
    val energyLevel:Int,

    @SerialName(value = "image")
    val image:ImageData? = null,

    val intelligence:Int,

//    @SerialName(value="wikipedia_url")
//    val wikipediaUrl:String
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Weight::class.java.classLoader),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readParcelable(ImageData::class.java.classLoader),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(temperament)
        parcel.writeString(description)
        parcel.writeParcelable(weight, flags)
        parcel.writeString(lifespan)
        parcel.writeInt(adaptability)
        parcel.writeInt(affectionLevel)
        parcel.writeInt(childFriendly)
        parcel.writeInt(dogFriendly)
        parcel.writeInt(sheddingLevel)
        parcel.writeInt(energyLevel)
        parcel.writeParcelable(image, flags)
        parcel.writeInt(intelligence)
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


