package ru.farkhodkhaknazarov.atlant.activities.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.jetbrains.anko.doAsync
import ru.farkhodkhaknazarov.atlant.activities.LoginActivity
import ru.farkhodkhaknazarov.atlant.activities.MainActivity
import ru.farkhodkhaknazarov.atlant.activities.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.atlant.api.websocket.EchoWebSocketListener
import ru.farkhodkhaknazarov.atlant.data.utils.RoomSingleton
import ru.farkhodkhaknazarov.atlant.utils.Constants

class MainActivityModule : AppCompatActivity() {
    private lateinit var mDb: RoomSingleton
    private lateinit var client: OkHttpClient
    private lateinit var ws: WebSocket

    companion object {

        lateinit var module: MainActivityModule

        fun deleteAllTokens() {
            var mDb = RoomSingleton.getInstance(LoginActivity.loginActivity.applicationContext)

            doAsync {
                mDb.tokensDao().deleteAllTokens()
            }
        }

        fun startLoginActivity() {
            MainActivity.mainActivity.finish()

            module.startLoginActivity()
        }

        fun setupModule(): MainActivityModule {
            module = MainActivityModule()
            return module
        }
    }

    fun onLogout() {
        mDb = RoomSingleton.getInstance(LoginActivity.loginActivity.applicationContext)
        doAsync {
            var token = mDb.tokensDao().allTokens().get(0)

            LoginModule().logoutUser(token)
        }
    }

    fun startLoginActivity() {
        val intent = Intent(MainActivity.mainActivity, LoginActivity::class.java)
        ContextCompat.startActivity(MainActivity.mainActivity, intent, null)
    }

    fun startOnClick() {
        client = OkHttpClient()

        val request = Request.Builder().url(Constants.wsUrl).build()
        val listener = EchoWebSocketListener()
        ws = client.newWebSocket(request, listener)

        client.dispatcher.executorService.shutdown()
    }

    fun wsOutput(message: String?) {
        MainActivityPresenter.mainActivityPresenter?.messageUpdate(message)
    }

    fun getUserProfile() {
        mDb = RoomSingleton.getInstance(LoginActivity.loginActivity.applicationContext)
        doAsync {
            var token = mDb.tokensDao().allTokens().get(0)

            LoginModule().getUserProfile(token.token)
        }
    }

    fun wsStop() {
        ws.close(EchoWebSocketListener.NORMAL_CLOSE_STATUS, "Bye")
    }

    override fun onDestroy() {
        super.onDestroy()
        ws.close(EchoWebSocketListener.NORMAL_CLOSE_STATUS, "Bye")
    }

}