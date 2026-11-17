package com.example.mdp.network

import com.example.mdp.GoldPriceStorage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GoldPriceRepository(
    private val storage: GoldPriceStorage
) {

    suspend fun updateGoldPrice(): Float {

        val formatter =
            SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.US
            )

        val today =
            Calendar.getInstance()

        val weekAgo =
            Calendar.getInstance().apply {
                add(
                    Calendar.DAY_OF_YEAR,
                    -7
                )
            }

        val dateFrom =
            formatter.format(
                weekAgo.time
            )

        val dateTo =
            formatter.format(
                today.time
            )

        val xml =
            CbrRetrofit.api
                .getMetalRates(
                    dateFrom,
                    dateTo
                )

        val price =
            parseLatestGoldPrice(
                xml
            )

        storage.save(
            price
        )

        return price
    }

    fun getCachedGoldPrice(): Float {

        return storage.load()
    }

    private fun parseLatestGoldPrice(
        xml: String
    ): Float {

        val regex =
            Regex(
                """<Record[^>]*Code="1"[^>]*>[\s\S]*?<Buy>([\d.,]+)</Buy>[\s\S]*?</Record>"""
            )

        val matches =
            regex.findAll(
                xml
            ).toList()

        if (matches.isEmpty()) {
            return storage.load()
        }

        val rawPrice =
            matches.last()
                .groupValues[1]

        return rawPrice
            .replace(",", ".")
            .toFloatOrNull()
            ?: storage.load()
    }
}