package dev.beriashvili.exams.firstmidterm.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class LaunchesModel() : Parcelable {
    @SerializedName("flight_number")
    var flightNumber: Int? = 0

    @SerializedName("mission_name")
    var missionName: String? = ""

    @SerializedName("launch_date_utc")
    var launchDate: String? = ""

    var rocket: Rocket? = Rocket()

    class Rocket {
        @SerializedName("rocket_name")
        var rocketName: String? = ""

        override fun toString(): String {
            return "Rocket(rocketName=$rocketName)"
        }
    }

    @SerializedName("launch_success")
    var launchSuccess: Boolean? = false

    var links: Links? = Links()

    class Links {
        @SerializedName("flickr_images")
        var images: List<String?>? = listOf()

        override fun toString(): String {
            return "Links(images=$images)"
        }
    }

    var details: String? = ""

    constructor(parcel: Parcel) : this() {
        flightNumber = parcel.readValue(Int::class.java.classLoader) as? Int
        missionName = parcel.readString()
        launchDate = parcel.readString()
        launchSuccess = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        details = parcel.readString()
    }

    override fun toString(): String {
        return "LaunchModel(flightNumber=$flightNumber, missionName=$missionName, launchDate=$launchDate, rocket=$rocket, launchSuccess=$launchSuccess, links=$links, details=$details)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(flightNumber)
        parcel.writeString(missionName)
        parcel.writeString(launchDate)
        parcel.writeValue(launchSuccess)
        parcel.writeString(details)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LaunchesModel> {
        override fun createFromParcel(parcel: Parcel): LaunchesModel {
            return LaunchesModel(parcel)
        }

        override fun newArray(size: Int): Array<LaunchesModel?> {
            return arrayOfNulls(size)
        }
    }
}
