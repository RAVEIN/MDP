package com.example.mdp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.mdp.data.RecordItem
import java.text.DateFormat
import java.util.Date

class RecordAdapter(
    context: Context,
    private val records: List<RecordItem>
) : ArrayAdapter<RecordItem>(
    context,
    R.layout.item_record,
    records
) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view =
            convertView ?: LayoutInflater
                .from(context)
                .inflate(
                    R.layout.item_record,
                    parent,
                    false
                )

        val record =
            records[position]

        val textName =
            view.findViewById<TextView>(
                R.id.textRecordName
            )

        val textScore =
            view.findViewById<TextView>(
                R.id.textRecordScore
            )

        val textDetails =
            view.findViewById<TextView>(
                R.id.textRecordDetails
            )

        val date =
            DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT
            ).format(
                Date(record.timestamp)
            )

        textName.text =
            record.playerName

        textScore.text =
            "Очки: ${record.score}"

        textDetails.text =
            """
            Сложность: ${record.difficulty}
            Попадания: ${record.hits}
            Промахи: ${record.misses}
            Точность: ${record.accuracy}%
            $date
            """.trimIndent()

        return view
    }
}