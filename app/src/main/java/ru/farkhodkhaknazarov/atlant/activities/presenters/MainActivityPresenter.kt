package ru.farkhodkhaknazarov.atlant.activities.presenters

import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import ru.farkhodkhaknazarov.atlant.R
import ru.farkhodkhaknazarov.atlant.activities.MainActivity
import ru.farkhodkhaknazarov.atlant.activities.modules.MainActivityModule
import ru.farkhodkhaknazarov.atlant.api.entities.UserInfo

class MainActivityPresenter {
    lateinit var view: MainActivity
    lateinit var module: MainActivityModule

    companion object {
        var mainActivityPresenter: MainActivityPresenter? = null

        fun setupMainActivityPresenter(presenter: MainActivityPresenter) {
            mainActivityPresenter = presenter
        }

        fun updateUserProfile(userInfo: UserInfo) {
            MainActivity.mainActivity.txtWFirstName.text = userInfo.info?.profiles?.get(0)?.first_name
            MainActivity.mainActivity.txtWLastName.text = userInfo.info?.profiles?.get(0)?.last_name
        }
    }

    class MainActivityRunable(bundle: () -> Unit) : Runnable {
        override fun run() {

        }

    }

    fun setupView() {
        MainActivityPresenter.setupMainActivityPresenter(this)

        view = MainActivity.mainActivity

        module = MainActivityModule.setupModule()

        view.btnLogout = view.findViewById(R.id.btnLogout)

        view.btnLogout.setOnClickListener(View.OnClickListener {
            module.onLogout()
        })

        view.btnStart = view.findViewById(R.id.btnStart)
        view.btnStart.setOnClickListener(View.OnClickListener {
            module.startOnClick()
        })

        view.btnStop = view.findViewById(R.id.btnStop)
        view.btnStop.setOnClickListener(View.OnClickListener {
            module.wsStop()
        })

        view.btnClear = view.findViewById(R.id.btnClear)
        view.btnClear.setOnClickListener {
            view.message.text = ""
        }

        view.message = view.findViewById(R.id.eTxMessages)

        view.firstName = view.findViewById(R.id.txtWFirstName)
        view.lastName = view.findViewById(R.id.txtWLastName)
    }

    fun showMessage(message: String) {
        view.showMessage(message)
    }

    fun messageUpdate(message: String?) {

        MainActivity.mainActivity.runOnUiThread(
            Runnable {
                view.message.text = "${view.message.text} \n\n $message"
            }
        )
    }

    fun fillUserProfile() {
        module.getUserProfile()
    }
}