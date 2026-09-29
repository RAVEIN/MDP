package com.example.mdp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment

class RulesFragment :
    Fragment(R.layout.fragment_rules) {

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(
            view,
            savedInstanceState
        )


        val textRules =
            view.findViewById<TextView>(
                R.id.textRules
            )


        val html =
            resources
                .openRawResource(
                    R.raw.rules
                )
                .bufferedReader(
                    Charsets.UTF_8
                )
                .use {
                    it.readText()
                }


        textRules.text =
            HtmlCompat.fromHtml(
                html,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
    }
}