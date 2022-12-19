package com.example.milestone2.contacts_app

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import com.example.milestone2.R
import com.example.milestone2.data_classes.Contacts
import kotlinx.coroutines.runBlocking

class AddAndModifyContact:DialogFragment() {
    private lateinit var fragmentView: View
    private lateinit var savebtn:Button
    private lateinit var closeBtn: AppCompatImageButton
    private lateinit var personName: EditText
    private lateinit var phoneNumber:EditText
    private lateinit var titleTextView: TextView
    lateinit var contactViewModel: ContactViewModel
    private var onDismissListener: DialogInterface.OnDismissListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.fragment_add_modify_contact, container, false)

        savebtn = fragmentView.findViewById(R.id.save_contact_btn)
        personName = fragmentView.findViewById(R.id.full_name)
        phoneNumber = fragmentView.findViewById(R.id.contact_number)
        closeBtn = fragmentView.findViewById(R.id.close_add_fragment)
        titleTextView = fragmentView.findViewById(R.id.create_contact_text)
        return fragmentView
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val mArgs = arguments
        val purpose = mArgs!!.getString("purpose")

        if(purpose.equals("Create"))
        {
            buttonListeners(false)
        }
        else{
            titleTextView.text = "Update Contact"
            val person_name = mArgs.getString("person_name")
            val contact_number = mArgs.getString("contact_number")
            personName.setText(person_name)
            phoneNumber.setText(contact_number)
            buttonListeners(true)
        }
    }

    private fun buttonListeners(purpose:Boolean) // 1 for modify 0 for create
    {
        savebtn.setOnClickListener {
            if (fieldCheck()) {

                if(!purpose)
                {
                    contactViewModel.insert(Contacts(personName.text.toString(),phoneNumber.text.toString()))

                    Toast.makeText(
                        fragmentView.context,
                        "Contact Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{
                    runBlocking{
                        contactViewModel.update(Contacts(personName.text.toString(),phoneNumber.text.toString()))
                    }

                    Toast.makeText(
                        fragmentView.context,
                        "Contact Updated Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }


                val manager = requireActivity().supportFragmentManager
                manager.beginTransaction().remove(this).commit()
            } else {
                Toast.makeText(
                    fragmentView.context,
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
            this.onDismissListener!!.onDismiss(dialog)
        }
    }

}