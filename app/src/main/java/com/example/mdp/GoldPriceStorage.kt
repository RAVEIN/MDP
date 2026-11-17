package com.example.mdp

import android.content.Context

class GoldPriceStorage(
    context: Context
) {
    private val preferences =
        context.getSharedPreferences(
            "gold_price",
            Context.MODE_PRIVATE
        )
    fun save(
        price: Float
    ) {
        preferences.edit()
            .putFloat(
                "price",
                price
            ) .apply()
    }

    fun load(): Float {
        return preferences.getFloat(
            "price",
            0f
        )
    }
}