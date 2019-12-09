package ru.farkhodkhaknazarov.atlant.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "usertokens")
data class UserTokens(
    @PrimaryKey
    @ColumnInfo(name = "accountid")
    var account_id: String,
    @ColumnInfo(name = "sessionid")
    var session_id: String,
    @ColumnInfo(name = "accounttype")
    var account_type: String,
    @ColumnInfo(name = "exp")
    var exp: Long,
    @ColumnInfo(name = "pass")
    var pass: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "token")
    var token: String
    )