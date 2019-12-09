package ru.farkhodkhaknazarov.atlant.activities

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import ru.farkhodkhaknazarov.atlant.R
import ru.farkhodkhaknazarov.atlant.activities.presenters.LoginActivityPresenter

class LoginActivity : AppCompatActivity() {

    lateinit var presenter: LoginActivityPresenter
    lateinit var btnOK: Button

    lateinit var loginProgressBar: ProgressBar

    lateinit var tilEmail: TextInputLayout
    lateinit var tilPassword: TextInputLayout

    companion object {
        lateinit var loginActivity: LoginActivity

        fun setupLoginActivity(activity: LoginActivity) {
            loginActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginActivity.setupLoginActivity(this)

        presenter = LoginActivityPresenter()

        presenter.checkToken()

    }

    fun tokenResult(result: Boolean) {
        if (result) {
            presenter.startMainActivity()
        } else {
            runOnUiThread {
                presenter.setupView(this)
            }
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

}
