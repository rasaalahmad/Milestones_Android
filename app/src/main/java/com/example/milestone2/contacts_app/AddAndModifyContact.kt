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
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.example.milestone2.R
import com.example.milestone2.data_classes.Contacts
import kotlinx.coroutines.runBlocking

class AddAndModifyContact(private val contactViewModel: ContactViewModel):DialogFragment() {
    private lateinit var fragmentView: View
    private lateinit var savebtn:Button
    private lateinit var closeBtn: AppCompatImageButton
    private lateinit var personName: EditText
    private lateinit var phoneNumber:EditText
    private lateinit var titleTextView: TextView
    private var onDismissListener: DialogInterface.OnDismissListener? = null
    private var uid:Int = 0

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addUpdateCheck()
    }

    override fun onResume() {
        super.onResume()
        addUpdateCheck()
    }

    @SuppressLint("SetTextI18n")
    private fun addUpdateCheck()
    {
        val mArgs = arguments
        val isUpdate = mArgs!!.getBoolean("isUpdate")

        if(!isUpdate)
        {
            personName.setText("")
            phoneNumber.setText("")
            personName.setHint(R.string.full_name)
            phoneNumber.setHint(R.string.phone_number)
            buttonListeners(false)
        }
        else{
            titleTextView.text = "Update Contact"
            uid = mArgs.getInt("uid")
            val person_name = mArgs.getString("person_name")
            val contact_number = mArgs.getString("contact_number")
            personName.setText(person_name)
            phoneNumber.setText(contact_number)
            buttonListeners(true)
        }
    }

    private fun buttonListeners(isUpdate:Boolean) // 1 for modify 0 for create
    {
        savebtn.setOnClickListener {
            if (fieldCheck()) {

                if(!isUpdate)
                {
                    runBlocking {
                        contactViewModel.insert(
                            Contacts(
                                personName.text.toString(),
                                phoneNumber.text.toString()
                            )
                        )
                    }
                    Toast.makeText(
                        fragmentView.context,
                        "Contact Added Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else{

                    runBlocking{
                        val contact:Contacts = Contacts(personName.text.toString(),phoneNumber.text.toString())
                        contact.uid = uid
                        contactViewModel.updateContact(contact)
                        //Log.d("Update Check",contactViewModel.getAllContacts())
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