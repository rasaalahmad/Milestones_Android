package com.example.milestone2.ui.add_and_modify_contact

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import com.example.milestone2.ContactsNavigationDrawerActivity
import com.example.milestone2.R
import com.example.milestone2.classes.Contacts
import com.example.milestone2.classes.NotificationServiceClass
import com.example.milestone2.ui.home.HomeFragment
import com.example.milestone2.ui.home.HomeViewModel
import com.example.milestone2.ui.image_bottom_sheet.ImageUploadBottomSheetFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking

class AddAndModifyContact(private var homeViewModel: HomeViewModel):DialogFragment() {
    private lateinit var fragmentView: View
    private lateinit var savebtn:Button
    private lateinit var closeBtn: AppCompatImageButton
    private lateinit var personName: EditText
    private lateinit var phoneNumber:EditText
    private lateinit var titleTextView: TextView
    private var onDismissListener: DialogInterface.OnDismissListener? = null
    private var uid:Int = 0
    private lateinit var intent:Intent
    private lateinit var notificationServiceClass: NotificationServiceClass
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var uploadImageButton: AppCompatButton
    private var absoluteImagePath:String = ""

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
        uploadImageButton = fragmentView.findViewById(R.id.image_upload_button)
        intent = Intent(context, ContactsNavigationDrawerActivity::class.java)
        notificationServiceClass = NotificationServiceClass(requireContext())
        return fragmentView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        firebaseAnalytics = Firebase.analytics
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addUpdateCheck()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        addUpdateCheck()
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
            homeViewModel.mutableLiveContact.value!!.person_name = mArgs.getString("person_name")
            homeViewModel.mutableLiveContact.value!!.contact_number = mArgs.getString("contact_number")
            homeViewModel.mutableLiveContact.value!!.image_path = mArgs.getString("image_path").toString()
            personName.setText(homeViewModel.mutableLiveContact.value!!.person_name)
            phoneNumber.setText(homeViewModel.mutableLiveContact.value!!.contact_number)
            buttonListeners(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun buttonListeners(isUpdate:Boolean) // 1 for modify 0 for create
    {
        savebtn.setOnClickListener {
            if (fieldCheck()) {

                if(!isUpdate)
                {
                    homeViewModel.mutableLiveContact.value!!.person_name = personName.text.toString()
                    homeViewModel.mutableLiveContact.value!!.contact_number = phoneNumber.text.toString()

                    runBlocking {
                        homeViewModel.insert(
                            homeViewModel.mutableLiveContact.value!!
                        )
                        notificationServiceClass.createNotification("New Contact",
                            "${personName.text} added in Contacts", intent)
                    }
                    Toast.makeText(
                        fragmentView.context,
                        resources.getString(R.string.contact_added_message),
                        Toast.LENGTH_SHORT
                    ).show()

                    firebaseAnalytics.logEvent("contact_inserted") {
                        param(FirebaseAnalytics.Param.ITEM_ID, "3")
                        param(FirebaseAnalytics.Param.ITEM_NAME, "contact insertion in database")
                    }
                }
                else{

                    runBlocking{
                        val contact:Contacts = homeViewModel.mutableLiveContact.value!!
                        contact.uid = uid
                        homeViewModel.updateContact(contact)
                        //Log.d("Update Check",contactViewModel.getAllContacts())
                    }

                    Toast.makeText(
                        fragmentView.context,
                        resources.getString(R.string.contact_update_message),
                        Toast.LENGTH_SHORT
                    ).show()

                    firebaseAnalytics.logEvent("contact_updated") {
                        param(FirebaseAnalytics.Param.ITEM_ID, "4")
                        param(FirebaseAnalytics.Param.ITEM_NAME, "contact updated in database")
                    }
                }


                val manager = requireActivity().supportFragmentManager
                manager.beginTransaction().remove(this).commit()

            } else {
                Toast.makeText(
                    fragmentView.context,
                    resources.getString(R.string.enter_complete_details),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        closeBtn.setOnClickListener{
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }

        uploadImageButton.setOnClickListener {
            val imageUploadBottomSheetFragment = ImageUploadBottomSheetFragment(homeViewModel)
            imageUploadBottomSheetFragment.setTargetFragment(this, 123)
            imageUploadBottomSheetFragment.show(requireActivity().supportFragmentManager,null)
        }
    }

    private fun fieldCheck():Boolean
    {
        return (personName.text.toString() != "" && phoneNumber.text.toString() != "");
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