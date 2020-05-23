package dev.beriashvili.exams.firstmidterm.models

import android.os.Parcel
import android.os.Parcelable

class Query() : Parcelable {
    lateinit var sort: String
    lateinit var order: String
    var offset: Int = 0
    var limit: Int = 0
    lateinit var filter: String
    lateinit var parameters: MutableMap<String, String>

    constructor(parcel: Parcel) : this() {
        sort = parcel.readString()!!
        order = parcel.readString()!!
        offset = parcel.readInt()
        limit = parcel.readInt()
        filter = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sort)
        parcel.writeString(order)
        parcel.writeInt(offset)
        parcel.writeInt(limit)
        parcel.writeString(filter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Query> {
        override fun createFromParcel(parcel: Parcel): Query {
            return Query(parcel)
        }

        override fun newArray(size: Int): Array<Query?> {
            return arrayOfNulls(size)
        }
    }
}