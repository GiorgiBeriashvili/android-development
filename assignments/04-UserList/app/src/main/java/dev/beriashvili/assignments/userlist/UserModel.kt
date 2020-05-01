package dev.beriashvili.assignments.userlist

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class UserModel {
    var page = 0

    @SerializedName("per_page")
    var perPage = 0

    var total = 0

    @SerializedName("total_pages")
    var totalPages = 0

    var data = ArrayList<Data>()

    class Data() : Parcelable {
        var id = 0
        var email = ""

        @SerializedName("first_name")
        var firstName = ""

        @SerializedName("last_name")
        var lastName = ""

        var avatar = ""

        constructor(parcel: Parcel) : this() {
            id = parcel.readInt()
            email = parcel.readString()!!
            firstName = parcel.readString()!!
            lastName = parcel.readString()!!
            avatar = parcel.readString()!!
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(email)
            parcel.writeString(firstName)
            parcel.writeString(lastName)
            parcel.writeString(avatar)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Data> {
            override fun createFromParcel(parcel: Parcel): Data {
                return Data(parcel)
            }

            override fun newArray(size: Int): Array<Data?> {
                return arrayOfNulls(size)
            }
        }
    }

    class User {
        var data = Data()
    }
}