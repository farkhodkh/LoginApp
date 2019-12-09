package ru.farkhodkhaknazarov.atlant.api.websocket

import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import ru.farkhodkhaknazarov.atlant.activities.modules.MainActivityModule

class EchoWebSocketListener: WebSocketListener() {
    companion object{
        val NORMAL_CLOSE_STATUS = 1000
        private val bitcoin_address = "1qEZTy24ANV4q4xoaZ4JrNkg9HbUKqTC9"
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
//        webSocket.send("{\"op\":\"ping_block\"}")
        webSocket.send("{\"op\":\"addr_sub\", \"addr\":\"$bitcoin_address\"}")
        MainActivityModule.module.wsOutput("Connected")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(EchoWebSocketListener.NORMAL_CLOSE_STATUS, "Bye")
        MainActivityModule.module.wsOutput("Connection closed")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(EchoWebSocketListener.NORMAL_CLOSE_STATUS, "{\"op\":\"addr_unsub\", \"addr\":\"$bitcoin_address\"}")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        MainActivityModule.module.wsOutput(t.message)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        if(text.contains("utx")){
            TransactionsController.readTransaction(text)
        }else {
            MainActivityModule.module.wsOutput(text)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        MainActivityModule.module.wsOutput(bytes.hex())
    }
}