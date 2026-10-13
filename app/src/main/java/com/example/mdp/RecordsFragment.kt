package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mdp.data.AppDatabase
import kotlinx.coroutines.launch

class RecordsFragment :
    Fragment(R.layout.fragment_records) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        val database =
            AppDatabase.getInstance(
                requireContext()
            )

        val listRecords =
            view.findViewById<ListView>(
                R.id.listRecords
            )

        val textEmpty =
            view.findViewById<TextView>(
                R.id.textEmptyRecords
            )

        listRecords.emptyView =
            textEmpty

        viewLifecycleOwner
            .lifecycleScope
            .launch {

                val records =
                    database.scoreDao()
                        .getRecords()

                listRecords.adapter =
                    RecordAdapter(
                        requireContext(),
                        records
                    )
            }
    }
}