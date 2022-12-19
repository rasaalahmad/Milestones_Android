package com.example.milestone2.contacts_app

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R
import com.example.milestone2.adapters.ContactsRecyclerViewAdapter
import com.example.milestone2.data_classes.ContactsObject
import kotlinx.coroutines.runBlocking


class ContactsMain:Fragment(R.layout.fragment_contacts_main) {
    private lateinit var addContactbtn: ImageButton
    private lateinit var fragment_view: View
    lateinit var contactViewModel: ContactViewModel
    lateinit var  contactsAdapter: ContactsRecyclerViewAdapter
    private val addContact = AddContact()
    private lateinit var rView:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragment_view = inflater.inflate(R.layout.fragment_contacts_main, container, false)

        addContactbtn = fragment_view.findViewById(R.id.add_contact_btn)
        rView = fragment_view.findViewById(R.id.contacts_recycler_view)
        rView.layoutManager = LinearLayoutManager(context)
        listeners()

        return fragment_view
    }

    override fun onResume() {
        super.onResume()
        contactsAdapter = ContactsRecyclerViewAdapter(ContactsObject.allContacts, object : ContactsRecyclerViewAdapter.OptionsMenuClickListener{
            // implement the required method
            override fun onOptionsMenuClicked(position: Int) {
                // this method will handle the onclick options click
                // it is defined below
                performOptionsMenuClick(position)
            }
        })
        rView.adapter = contactsAdapter
    }

    private fun listeners()
    {
        ContactsObject.allContacts = contactViewModel.getAllContacts()
        contactsAdapter = ContactsRecyclerViewAdapter(ContactsObject.allContacts, object : ContactsRecyclerViewAdapter.OptionsMenuClickListener{
            // implement the required method
            override fun onOptionsMenuClicked(position: Int) {
                // this method will handle the onclick options click
                // it is defined below
                performOptionsMenuClick(position)
            }
        })
        rView.adapter = contactsAdapter

        addContact.setOnDismissListener {
            ContactsObject.allContacts = contactViewModel.getAllContacts()
        }

        addContactbtn.setOnClickListener{
            Log.d("Test click","I am here")
            addContact.show(requireActivity().supportFragmentManager,"CreateNewContact")
            addContact.contactViewModel = contactViewModel
        }
    }

    // this method will handle the onclick options click
    private fun performOptionsMenuClick(position: Int) {
        // create object of PopupMenu and pass context and view where we want
        // to show the popup menu
        val popupMenu = PopupMenu(activity as Context, rView[position])
        // add the menu
        popupMenu.inflate(R.menu.pop_up_menu)
        // implement on menu item click Listener
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.delete -> {

                        val dialogClickListener =
                            DialogInterface.OnClickListener { dialog, which ->
                                when (which) {
                                    DialogInterface.BUTTON_POSITIVE -> {
                                        deleteContact(position)
                                    }
                                    DialogInterface.BUTTON_NEGATIVE -> {
                                        dialog.cancel()
                                    }
                                }
                            }
                        alertDialogFun(dialogClickListener)

                        return true
                    }
                    R.id.update -> {
                        // define
                        //Toast.makeText(this@MainActivity , "Item 2 clicked" , Toast.LENGTH_SHORT).show()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }

    fun alertDialogFun(dialogClickListener: DialogInterface.OnClickListener)
    {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    fun deleteContact(position:Int)
    {
        runBlocking {
            contactViewModel.delete(ContactsObject.allContacts[position])
        }
        ContactsObject.allContacts.drop(position)
        contactsAdapter = ContactsRecyclerViewAdapter(ContactsObject.allContacts, object : ContactsRecyclerViewAdapter.OptionsMenuClickListener{
            // implement the required method
            override fun onOptionsMenuClicked(position: Int) {
                // this method will handle the onclick options click
                // it is defined below
                performOptionsMenuClick(position)
            }
        })
        rView.adapter = contactsAdapter
    }
}