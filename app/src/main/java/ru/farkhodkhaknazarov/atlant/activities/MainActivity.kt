package ru.farkhodkhaknazarov.atlant.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import ru.farkhodkhaknazarov.atlant.R
import ru.farkhodkhaknazarov.atlant.activities.presenters.MainActivityPresenter

class MainActivity : AppCompatActivity() {

    lateinit var btnLogout: Button
    lateinit var btnStart: Button
    lateinit var btnStop: Button
    lateinit var btnClear: Button
    lateinit var message:TextView
    lateinit var firstName: TextView
    lateinit var lastName: TextView

    var presenter: MainActivityPresenter? = null

    companion object{
        lateinit var mainActivity: MainActivity

        fun setupMainActivity(activity: MainActivity){
            mainActivity = activity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MainActivity.setupMainActivity(this)

        presenter = MainActivityPresenter()

        presenter?.setupView()

        presenter?.fillUserProfile()

    }

    fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
