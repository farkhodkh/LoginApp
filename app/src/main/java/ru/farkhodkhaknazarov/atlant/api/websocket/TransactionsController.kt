package ru.farkhodkhaknazarov.atlant.api.websocket

import com.google.gson.GsonBuilder
import org.json.JSONObject
import ru.farkhodkhaknazarov.atlant.activities.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.atlant.api.entities.Container
import ru.farkhodkhaknazarov.atlant.api.entities.Input
import ru.farkhodkhaknazarov.atlant.api.entities.Transaction
import java.util.*

class TransactionsController {

    companion object {
        fun readTransaction(text: String) {
            var gson = GsonBuilder().setPrettyPrinting().create()
            var transaction = gson.fromJson(text, Transaction::class.java)

            var jsonContainer = JSONObject(text).getString("x")

            var container: Container = gson.fromJson(jsonContainer, Container::class.java)

            var jsonInputs = JSONObject(text).getString("inputs")

            for(input in arrayListOf<Input>(*container.inputs)){
                MainActivityPresenter.mainActivityPresenter?.messageUpdate("Value: ${input.value}")
            }

        }
    }
}