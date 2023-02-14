package com.example.milestone2.ui.image_bottom_sheet

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.milestone2.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ImageUploadBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var cameraButton:AppCompatButton
    private lateinit var galleryButton:AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_upload_bottom_sheet, container, false)
        cameraButton = view.findViewById(R.id.camera_button)
        galleryButton = view.findViewById(R.id.gallery_button)
        buttonListeners()
        return view
    }

    private fun buttonListeners() {
        cameraButton.setOnClickListener {
            val REQUEST_IMAGE_CAPTURE = 1
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        galleryButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }
    }
}