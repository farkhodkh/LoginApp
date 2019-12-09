package ru.farkhodkhaknazarov.atlant.activities.modules

import android.content.Intent
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.doAsync
import ru.farkhodkhaknazarov.atlant.activities.LoginActivity
import ru.farkhodkhaknazarov.atlant.activities.MainActivity
import ru.farkhodkhaknazarov.atlant.activities.presenters.LoginActivityPresenter
import ru.farkhodkhaknazarov.atlant.api.entities.Login
import ru.farkhodkhaknazarov.atlant.data.room.entities.UserTokens
import ru.farkhodkhaknazarov.atlant.data.utils.RoomSingleton
import java.util.*

class LoginActivityModule {

    private lateinit var mDb: RoomSingleton

    companion object {
        var loginActivityModule: LoginActivityModule = LoginActivityModule()

        fun saveToken(mDb: RoomSingleton?, token: UserTokens?) {
            var compDb: RoomSingleton

            if (mDb == null) {
                compDb = RoomSingleton.getInstance(LoginActivity.loginActivity.applicationContext)
            } else {
                compDb = mDb
            }

            doAsync {
                token?.let { compDb.tokensDao().insert(it) }

                LoginActivityModule.readToken()
            }

        }

        fun readToken() {

            var mDb = RoomSingleton.getInstance(LoginActivity.loginActivity.applicationContext)

            doAsync {
                var tokensList = mDb.tokensDao().allTokens()

                if (tokensList.size > 0) {
                    tokensList.forEach {

                        if(Date(it.exp) < Date()){
                            doAsync {
                                LoginModule().refreshToken(it)
                            }
                        }else{
                            LoginActivity.loginActivity.tokenResult(Date(it.exp) > Date())
                        }
                    }
                } else {
                    LoginActivity.loginActivity.tokenResult(false)
                }
            }
        }
    }

    fun onBtnClickListener(tilEmail: TextInputLayout, tilPassword: TextInputLayout) {

        val email = tilEmail.editText?.text
        val pass = tilPassword.editText?.text

        if (email?.length == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            LoginActivityPresenter.loginActivityPresenter.showError("Wrong E-Mail")
            return
        }

        if (pass?.length == 0) {
            LoginActivityPresenter.loginActivityPresenter.showError("Wrong password")
            return
        }

        mDb = RoomSingleton.getInstance(LoginActivity.loginActivity.applicationContext)

        doAsync {

            val token = LoginModule().registerUser(Login(email.toString(), pass.toString()))

//            token?.save(mDb, token)
        }
    }

    fun checkToken() {
        LoginActivityModule.readToken()
    }

    fun startMainActivity() {
        val intent = Intent(LoginActivity.loginActivity, MainActivity::class.java)
        ContextCompat.startActivity(LoginActivity.loginActivity, intent, null)
    }

    fun registrationError(message: String) {
        LoginActivityPresenter.loginActivityPresenter.registrationError()
    }

//    fun UserTokens.save(mDb: RoomSingleton?, token: UserTokens?) {
//        doAsync {
//            token?.let { mDb?.tokensDao()?.insert(it) }
//        }
//    }
}