package com.example.milestone2.contacts_app

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import com.example.milestone2.R
import com.example.milestone2.data_classes.Contacts
import com.example.milestone2.data_classes.ContactsObject

class AddContact:DialogFragment() {
    private lateinit var fragment_view: View
    private lateinit var savebtn:Button
    private lateinit var closeBtn: AppCompatImageButton
    private lateinit var personName: EditText
    private lateinit var phoneNumber:EditText
    lateinit var contactViewModel: ContactViewModel
    private var onDismissListener: DialogInterface.OnDismissListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragment_view = inflater.inflate(R.layout.fragment_add_contact, container, false)

        savebtn = fragment_view.findViewById(R.id.save_contact_btn)
        personName = fragment_view.findViewById(R.id.full_name)
        phoneNumber = fragment_view.findViewById(R.id.contact_number)
        closeBtn = fragment_view.findViewById(R.id.close_add_fragment)
        return fragment_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        buttonListeners()
    }

    private fun buttonListeners()
    {
        savebtn.setOnClickListener {
            if (fieldCheck()) {
                Log.d("Contact check 1", personName.text.toString())
                Log.d("Contact check 1", phoneNumber.text.toString())

                contactViewModel.insert(Contacts(personName.text.toString(),phoneNumber.text.toString()))

                Toast.makeText(
                    fragment_view.context,
                    "Contact Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                val manager = requireActivity().supportFragmentManager
                manager.beginTransaction().remove(this).commit()
            } else {
                Toast.makeText(
                    fragment_view.context,
                    "Enter complete details!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        closeBtn.setOnClickListener{
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }
    }

    private fun fieldCheck():Boolean
    {
        if(personName.text.toString() == "" || phoneNumber.text.toString() == "")
        {
            return false
        }
        return true
    }

    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener)
    {
        this.onDismissListener = onDismissListener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (onDismissListener != null) {
            this.onDismissListener!!.onDismiss(dialog);
        }
    }

}