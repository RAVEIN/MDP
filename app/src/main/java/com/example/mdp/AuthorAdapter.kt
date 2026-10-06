package com.example.mdp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class AuthorAdapter(
    context: Context,
    private val authors: List<Author>
) : ArrayAdapter<Author>(
    context,
    R.layout.item_author,
    authors
) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val view =
            convertView
                ?: LayoutInflater
                    .from(context)
                    .inflate(
                        R.layout.item_author,
                        parent,
                        false
                    )


        val author =
            authors[position]


        val imageAuthor =
            view.findViewById<ImageView>(
                R.id.imageAuthor
            )


        val textAuthorName =
            view.findViewById<TextView>(
                R.id.textAuthorName
            )


        imageAuthor.setImageResource(
            author.imageResource
        )


        textAuthorName.text =
            author.name


        return view
    }
}