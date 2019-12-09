package ru.farkhodkhaknazarov.atlant.api.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Login(
    @SerializedName("email")
    @Expose
    var email: String,
    @SerializedName("password")
    @Expose
    var password: String
)