package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlaceholderFragment :
    Fragment(R.layout.fragment_placeholder) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val text =
            view.findViewById<TextView>(
                R.id.textPlaceholder
            )

        text.text =
            requireArguments()
                .getString(ARG_TEXT)
    }


    companion object {

        private const val ARG_TEXT =
            "text"


        fun newInstance(
            text: String
        ): PlaceholderFragment {

            val fragment =
                PlaceholderFragment()

            fragment.arguments =
                Bundle().apply {

                    putString(
                        ARG_TEXT,
                        text
                    )
                }

            return fragment
        }
    }
}