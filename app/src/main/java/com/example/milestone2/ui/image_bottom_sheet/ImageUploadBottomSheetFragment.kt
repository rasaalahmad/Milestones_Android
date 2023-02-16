package com.example.milestone2.ui.image_bottom_sheet

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.example.milestone2.R
import com.example.milestone2.ui.home.HomeFragment
import com.example.milestone2.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class ImageUploadBottomSheetFragment(private var homeViewModel: HomeViewModel) : BottomSheetDialogFragment() {
    private lateinit var cameraButton:AppCompatButton
    private lateinit var galleryButton:AppCompatButton
    private var absoluteImagePath:String = ""

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
                startActivityForResult(takePictureIntent, pic_id)
            } catch (e: ActivityNotFoundException) {
                // display error state to the user
            }
        }

        galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_SELECT)
        }
    }

    // This method will help to retrieve the image
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Match the request 'pic id with requestCode
        if (requestCode == pic_id) {
            // BitMap is data structure of image file which store the image in memory
            val photo = data!!.extras!!["data"] as Bitmap?
            // Set the image in imageview for display
           // clickImageId.setImageBitmap(photo)

            // Create a unique file name for the captured image using a timestamp
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val imageFileName = "IMG_${timeStamp}.jpeg"

            // Save the captured image to the device's external storage
            val imageFile = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), imageFileName)
            val outputStream = FileOutputStream(imageFile)
            photo?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Add the image to the device's gallery
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val contentUri = Uri.fromFile(imageFile)
            mediaScanIntent.data = contentUri
            requireActivity().sendBroadcast(mediaScanIntent)
            Log.d("image_path",imageFile.absolutePath)

            homeViewModel.mutableLiveContact.value!!.image_path = imageFile.absolutePath
          //  listener?.onImageCaptured(absoluteImagePath)
        }
        else if(requestCode == REQUEST_IMAGE_SELECT){
            val selectedImageUri: Uri? = data?.data
            // Get the path of the selected image
            val selectedImagePath = getRealPathFromUri(selectedImageUri)
            Log.d("gallery_image_path",selectedImagePath.toString())
        }
    }

    private fun getRealPathFromUri(selectedImageUri: Uri?): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(selectedImageUri!!,
            projection,
            null,
            null,
            null)
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()
        return path
    }

    companion object {
        // Define the pic id
        private const val pic_id = 123
        private const val REQUEST_IMAGE_SELECT = 1234
    }
}