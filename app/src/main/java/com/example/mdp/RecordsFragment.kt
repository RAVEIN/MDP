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

    private lateinit var database: AppDatabase

    private lateinit var listRecords: ListView

    private lateinit var textEmpty: TextView


    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )


        database =
            AppDatabase.getInstance(
                requireContext()
            )


        listRecords =
            view.findViewById(
                R.id.listRecords
            )


        textEmpty =
            view.findViewById(
                R.id.textEmptyRecords
            )


        listRecords.emptyView =
            textEmpty
    }


    override fun onResume() {
        super.onResume()

        loadRecords()
    }


    private fun loadRecords() {

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