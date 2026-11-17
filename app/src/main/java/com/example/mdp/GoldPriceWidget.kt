package com.example.mdp

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.mdp.network.GoldPriceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GoldPriceWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(
            context,
            appWidgetManager,
            appWidgetIds
        )

        val repository =
            GoldPriceRepository(
                GoldPriceStorage(context)
            )

        val cachedPrice =
            repository.getCachedGoldPrice()

        appWidgetIds.forEach { widgetId ->
            updateWidget(
                context,
                appWidgetManager,
                widgetId,
                cachedPrice
            )
        }

        val pendingResult =
            goAsync()

        CoroutineScope(
            SupervisorJob() +
                    Dispatchers.IO
        ).launch {

            try {
                val price =
                    repository.updateGoldPrice()

                appWidgetIds.forEach { widgetId ->
                    updateWidget(
                        context,
                        appWidgetManager,
                        widgetId,
                        price
                    )
                }
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun updateWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        widgetId: Int,
        price: Float
    ) {
        val views =
            RemoteViews(
                context.packageName,
                R.layout.widget_gold_price
            )

        val priceText =
            if (price > 0f) {
                "%.2f ₽/г".format(price)
            } else {
                "Нет данных"
            }

        views.setTextViewText(
            R.id.textGoldPrice,
            priceText
        )

        appWidgetManager.updateAppWidget(
            widgetId,
            views
        )
    }
}