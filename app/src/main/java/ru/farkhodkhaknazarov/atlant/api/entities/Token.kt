package ru.farkhodkhaknazarov.atlant.api.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token(
    @SerializedName("token")
    @Expose
    var token: String
)