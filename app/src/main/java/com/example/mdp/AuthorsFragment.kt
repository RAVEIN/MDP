package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.fragment.app.Fragment

class AuthorsFragment :
    Fragment(R.layout.fragment_authors) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )


        val listAuthors =
            view.findViewById<ListView>(
                R.id.listAuthors
            )


        val authors = listOf(

            Author(
                "Огнивенко Дмитрий",
                R.drawable.photo1
            ),

            Author(
                "Овсянников Иван",
                R.drawable.defo
            )
        )


        listAuthors.adapter =
            AuthorAdapter(
                requireContext(),
                authors
            )
    }
}