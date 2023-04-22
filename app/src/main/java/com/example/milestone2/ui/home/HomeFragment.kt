package com.example.milestone2.ui.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone2.R
import com.example.milestone2.adapters.ContactsRecyclerViewAdapter
import com.example.milestone2.ui.add_and_modify_contact.AddAndModifyContact
import com.example.milestone2.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class HomeFragment : Fragment() {
    val homeViewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var addContactButton: FloatingActionButton
    lateinit var  contactsAdapter: ContactsRecyclerViewAdapter
    private lateinit var addAndModifyContact: AddAndModifyContact
    private lateinit var rView: RecyclerView
    private lateinit var crashTestButton: Button
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        addAndModifyContact = AddAndModifyContact(homeViewModel)
        addContactButton = root.findViewById(R.id.add_contact_btn)
        rView = root.findViewById(R.id.contacts_recycler_view)
        rView.layoutManager = LinearLayoutManager(context)

        crashTestButton = root.findViewById(R.id.test_crash)

        contactsAdapter = ContactsRecyclerViewAdapter(object : ContactsRecyclerViewAdapter.OptionsMenuClickListener{
            // implement the required method
            override fun onOptionsMenuClicked(position: Int) {
                // this method will handle the onclick options click
                // it is defined below
                performOptionsMenuClick(position)
            }
        })
        setListAdapter()
        rView.adapter = contactsAdapter
        listeners()

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
    }

    override fun onResume() {
        super.onResume()
        setListAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListAdapter()
    {
        runBlocking {
            homeViewModel.getAllContactsObserver().observe(activity as FragmentActivity) {
                contactsAdapter.setList(ArrayList(it))
            }
            contactsAdapter.notifyDataSetChanged()
        }
    }

    private fun listeners()
    {
        addAndModifyContact.setOnDismissListener {
            setListAdapter()
            firebaseAnalytics.logEvent("dismiss_button_clicked") {
                param(FirebaseAnalytics.Param.ITEM_ID, "1")
                param(FirebaseAnalytics.Param.ITEM_NAME, "close_dialog_fragment")
            }
        }

        addContactButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putBoolean("isUpdate", false)
            addAndModifyContact.arguments = bundle
            addAndModifyContact.show(requireActivity().supportFragmentManager,"CreateNewContact")
            setListAdapter()
            firebaseAnalytics.logEvent("add_contact_button_clicked") {
                param(FirebaseAnalytics.Param.ITEM_ID, "2")
                param(FirebaseAnalytics.Param.ITEM_NAME, "open_add_modify_dialog_fragment")
            }
        }

        crashTestButton.setOnClickListener {
          throw RuntimeException("Test Crash") // Force a crash
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
                                        Toast.makeText(activity as Context, resources.getString(R.string.contact_delete_message),
                                            Toast.LENGTH_SHORT).show()
                                        setListAdapter()

                                        firebaseAnalytics.logEvent("contact_deleted") {
                                            param(FirebaseAnalytics.Param.ITEM_ID, "5")
                                            param(FirebaseAnalytics.Param.ITEM_NAME, "contact deleted in database")
                                        }
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
                        modifyContact(position)
                        setListAdapter()
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }

    private fun alertDialogFun(dialogClickListener: DialogInterface.OnClickListener)
    {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage(resources.getString(R.string.dialog_message)).setPositiveButton(resources.getString(R.string.yes),
            dialogClickListener)
            .setNegativeButton(resources.getString(R.string.no), dialogClickListener).show()
    }

    private fun deleteContact(position:Int)
    {
        runBlocking {
            homeViewModel.delete(contactsAdapter.contactsList[position])
            setListAdapter()
        }
    }

    private fun modifyContact(position: Int)
    {
        val bundle = Bundle()
        bundle.putInt("uid",contactsAdapter.contactsList[position].uid)
        bundle.putString("person_name", contactsAdapter.contactsList[position].person_name)
        bundle.putString("contact_number", contactsAdapter.contactsList[position].contact_number)
        bundle.putString("image_path",contactsAdapter.contactsList[position].image_path)
        bundle.putBoolean("isUpdate", true)
        addAndModifyContact.arguments = bundle
        addAndModifyContact.show(requireActivity().supportFragmentManager,"CreateNewContact")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            crashTestButton.visibility = View.GONE
            Toast.makeText(activity?.baseContext, "Landscape Mode", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            crashTestButton.visibility = View.VISIBLE
            Toast.makeText(activity?.baseContext, "Portrait Mode", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}