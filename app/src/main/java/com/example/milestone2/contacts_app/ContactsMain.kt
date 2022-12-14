package com.example.milestone2.contacts_app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R


class ContactsMain:Fragment(R.layout.fragment_contacts_main) {

    private lateinit var fragment_view: View
    /*private lateinit var listener:FragmentActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            this.listener = context as FragmentActivity
        }
    }*/



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragment_view = inflater.inflate(R.layout.fragment_contacts_main, container, false)
        return fragment_view
    }
}