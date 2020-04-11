package dev.beriashvili.assignments.itemlist

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class ItemModel(
    var image: Uri,
    var title: String,
    var description: String,
    var creationDateTime: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Uri::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(image, flags)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(creationDateTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "ItemModel(image=$image, title='$title', description='$description', creationDateTime='$creationDateTime')"
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}