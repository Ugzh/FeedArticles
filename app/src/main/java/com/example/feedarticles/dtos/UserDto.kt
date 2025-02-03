package com.example.feedarticles.dtos


import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class UserDto(
    @Json(name = "id")
    val id: Long,
    @Json(name = "login")
    val login: String,
    @Json(name = "mdp")
    val mdp: String,
    @Json(name = "token")
    val token: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(login)
        parcel.writeString(mdp)
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserDto> {
        override fun createFromParcel(parcel: Parcel): UserDto {
            return UserDto(parcel)
        }

        override fun newArray(size: Int): Array<UserDto?> {
            return arrayOfNulls(size)
        }
    }
}