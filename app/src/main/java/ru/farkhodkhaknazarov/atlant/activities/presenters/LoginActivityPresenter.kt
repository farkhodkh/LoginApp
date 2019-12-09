package ru.farkhodkhaknazarov.atlant.activities.presenters

import android.view.View
import ru.farkhodkhaknazarov.atlant.R
import ru.farkhodkhaknazarov.atlant.activities.LoginActivity
import ru.farkhodkhaknazarov.atlant.activities.modules.LoginActivityModule

class LoginActivityPresenter {

    lateinit var view: LoginActivity
    lateinit var module: LoginActivityModule

    companion object {
        lateinit var loginActivityPresenter: LoginActivityPresenter

        fun setupLoginActivityPresenter(presenter: LoginActivityPresenter) {
            loginActivityPresenter = presenter
        }
    }

    fun setupView(loginActivity: LoginActivity) {

        LoginActivityPresenter.setupLoginActivityPresenter(this)

        view = loginActivity
//        module = LoginActivityModule()
        module = LoginActivityModule.loginActivityModule

        view.btnOK = view.findViewById(R.id.btnLoginOK)
        view.btnOK.visibility = View.VISIBLE

//        view.btnReadToken = view.findViewById(R.id.btnReadToken)
//        view.btnReadToken.visibility = View.VISIBLE

        view.tilPassword = view.findViewById(R.id.tilPassword)
        view.tilPassword.visibility = View.VISIBLE

        view.tilEmail = view.findViewById(R.id.tilEmail)
        view.tilEmail.visibility = View.VISIBLE

        view.loginProgressBar = view.findViewById(R.id.loginProgressBar)
        view.loginProgressBar.visibility = View.GONE

        view.btnOK.setOnClickListener(View.OnClickListener {
            module.onBtnClickListener(view.tilEmail, view.tilPassword)

            view.loginProgressBar.visibility = View.VISIBLE
        })

    }

    fun checkToken() {
        LoginActivityPresenter.setupLoginActivityPresenter(this)

        module = LoginActivityModule.loginActivityModule

        module.checkToken()
    }

    fun showError(message: String) {
        view.showMessage(message)
    }

    fun startMainActivity() {
        module.startMainActivity()

        LoginActivity.loginActivity.finish()
    }

    fun registrationError() {
        view.loginProgressBar.visibility = View.INVISIBLE
        view.showMessage("Registration error, try again later")
    }
}