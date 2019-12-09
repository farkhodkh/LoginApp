package ru.farkhodkhaknazarov.atlant.api.entities

import retrofit2.http.Field

//{
//    "info": {
//    "session": null,
//    "account": {
//        "account_id": "afe0f9e4-4164-4fc9-bf8f-3fd8c85ae802",
//        "account_type": "user",
//        "email": "hello@karta.com",
//        "email_verified": false,
//        "phone": "",
//        "totp_verified": false,
//        "2fa_method": null,
//        "password": "",
//        "created_at": "2019-11-06T14:45:38.515Z"
//        },
//    "profiles": [
//            {
//                "profile_id": "4e8a1831-2b78-4c8d-9fbf-aa4172e017dc",
//                "account_id": "afe0f9e4-4164-4fc9-bf8f-3fd8c85ae802",
//                "profile_type": "guest",
//                "first_name": "Jimmy",
//                "last_name": "Song",
//                "location": "",
//                "gender": null,
//                "phone_country": null,
//                "phone_number": null,
//                "email": "hello@karta.com",
//                "avatar_url": "",
//                "kyc_verified": false,
//                "langs_spoken_names": [],
//                "joined_at": "2019-11-06T14:45:38.517Z"
//            }
//        ]
//    }
//}


class UserInfo(
    var info: ProfileInfo
)

class ProfileInfo(var session: String?, var account: Account, var profiles: Array<Profile>)

class Account(
    var account_id: String,
    var account_type: String,
    var email: String,
    var email_verified: Boolean,
    var phone: String,
    var totp_verified: Boolean,
    @Field("2fa_method")
    var fa_method: String?,
    var password: String,
    var created_at: String
)

class Profile(
    var profile_id: String,
    var account_id: String,
    var profile_type: String,
    var first_name: String,
    var last_name: String,
    var location: String?,
    var gender: String?,
    var phone_country: String?,
    var phone_number: String?,
    var email: String,
    var avatar_url: String,
    var kyc_verified: Boolean,
    var langs_spoken_names: Array<LangsSpokenName>,
    var joined_at: String
)

class LangsSpokenName
