package dev.beriashvili.assignments.shortstories.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Story(
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
